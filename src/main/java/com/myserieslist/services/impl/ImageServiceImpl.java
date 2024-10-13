package com.myserieslist.services.impl;

import com.myserieslist.dto.ImageRecord;
import com.myserieslist.entity.Image;
import com.myserieslist.exceptions.MySeriesListException;
import com.myserieslist.services.FileService;
import com.myserieslist.services.ImageService;
import com.myserieslist.utils.FileUtils;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

@RequestScoped
public class ImageServiceImpl implements ImageService {

    @Inject
    FileService fileService;


    static final String[] filesExtensions;
    static {
        filesExtensions = new String[] {
                "image/jpeg",
                "image/png",
                "image/gif"
        };
    }

    @Override
    public Image addImage(ImageRecord imageRecord) throws IOException {
        byte[] imageContent = Base64.getDecoder().decode(imageRecord.imageBase64());
        String extension = FileUtils.getExtension(imageContent);
        if (
                !Arrays.asList(filesExtensions).contains(extension)
        ) {
            throw new MySeriesListException(
                    "The extension " + extension + " is not supported.",
                    400
            );
        }
        Image image = new Image();
        image.setDescImage(imageRecord.description());
        image.setActive(true);
        image.setCreatedAt(LocalDateTime.now());
        image.setHash(UUID.randomUUID().toString());
        fileService.createFileInVolume(imageContent, image.getHash());
        image.persist();
        return image;
    }

    @Override
    public List<Image> addImages(List<ImageRecord> images) throws IOException {
        List<Image> imagesReturn = new ArrayList<>();
        for (ImageRecord image: images) {
            imagesReturn.add(addImage(image));
        }
        return imagesReturn;
    }

    @Override
    public void removeImage(String hash) throws MySeriesListException {
        Image.update("active = false WHERE hash = :hash", Parameters.with("hash", hash));
    }

    @Override
    public ImageRecord getImage(String hash) {
        Image image = getImageQuery(List.of(hash))
                .firstResultOptional()
                .orElseThrow(()->new MySeriesListException("No result", 404));
        return new ImageRecord(
                fileService.getBase64ByHash(image.getHash()),
                image.getDescImage(),
                image.getHash()
        );
    }

    @Override
    public List<ImageRecord> getImages(Long serieId) {
        return null;
    }

    @Override
    public List<ImageRecord> getImages(List<String> hashs) {
        return getImageQuery(hashs)
                .list()
                .stream()
                .map(image->
                        new ImageRecord(
                                fileService.getBase64ByHash(image.getHash()),
                                image.getDescImage(),
                                image.getHash()
                        )
                )
                .toList();
    }

    private PanacheQuery<Image> getImageQuery(List<String> hashs) {
        return Image.find("hash in ( :hashs )", Parameters.with("hashs", hashs));
    }


}
