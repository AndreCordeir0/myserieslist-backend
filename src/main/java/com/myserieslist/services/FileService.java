package com.myserieslist.services;

import com.myserieslist.exceptions.MySeriesListException;

public interface FileService {

    void createFileInVolume(byte[] image, String hash) throws MySeriesListException;

    String getBase64ByHash(String hash);
}
