package com.myserieslist.services.impl;

import com.myserieslist.exceptions.MySeriesListException;
import com.myserieslist.services.FileService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;

@RequestScoped
public class LocalFileService implements FileService {

    @ConfigProperty(name = "my.series.list.volume.path")
    String volumePath;
    @Override
    public void createFileInVolume(byte[] image, String hash) throws MySeriesListException {
        File file = new File(filePath(hash));
        try {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                throw new MySeriesListException("Error creating file.", 500);
            }
            Files.write(file.toPath(), image);
        } catch (IOException e) {
            Log.error(e);
            throw new MySeriesListException("Error creating file.", 500);
        }
    }

    @Override
    public String getBase64ByHash(String hash) {
        String path = filePath(hash);
        try (InputStream in = new FileInputStream(path)) {
            byte[] imageBytes = in.readAllBytes();
            return Base64.getEncoder().encode(imageBytes).toString();
        } catch (Exception e) {
            Log.error(e);
            throw new MySeriesListException("Unexpected error during image encode.", 500);
        }
    }

    private String filePath(String hash) {
        return volumePath +
                "/" +
                hash;
    }
}
