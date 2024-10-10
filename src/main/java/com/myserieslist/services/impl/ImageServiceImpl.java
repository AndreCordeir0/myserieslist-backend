package com.myserieslist.services.impl;

import com.myserieslist.dto.ImageRecord;
import com.myserieslist.entity.Image;
import com.myserieslist.exceptions.MySeriesListException;
import com.myserieslist.services.ImageService;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class ImageServiceImpl implements ImageService {

    @ConfigProperty(name = "my.series.list.volume.path")
    String volumePath;

    @Override
    public Image addImage(ImageRecord imageRecord) throws IOException {
        Image image = new Image();
        image.setDescImage(imageRecord.desc());
        image.setActive(true);
        image.setCreatedAt(LocalDateTime.now());
        image.setHash(UUID.randomUUID().toString());
        byte[] imageContent = Base64.getDecoder().decode(imageRecord.imageBase64());
        createFileInVolume(imageContent, image.getHash());
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
        return null;
    }

    @Override
    public List<ImageRecord> getImages(String identifier) {
        return null;
    }

    private void createFileInVolume(byte[] image, String hash) throws IOException {
        File file = new File(filePath(hash));
        boolean fileCreated = file.createNewFile();
        if (!fileCreated)
            throw new RuntimeException("Image hash already exists");
        Files.write(file.toPath(), image);
    }

    private String filePath(String hash) {
        return new StringBuilder()
                .append(volumePath)
                .append("/")
                .append(hash)
                .toString();
    }
}
