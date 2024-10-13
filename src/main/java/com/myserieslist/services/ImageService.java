package com.myserieslist.services;

import com.myserieslist.dto.ImageRecord;
import com.myserieslist.entity.Image;
import com.myserieslist.exceptions.MySeriesListException;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    Image addImage(ImageRecord imageRecord) throws IOException;

    List<Image> addImages(List<ImageRecord> images) throws IOException;

    void removeImage(String hash) throws MySeriesListException;

    ImageRecord getImage(String hash);

    List<ImageRecord> getImages(Long serieId);

    List<ImageRecord> getImages(List<String> hashs);


}
