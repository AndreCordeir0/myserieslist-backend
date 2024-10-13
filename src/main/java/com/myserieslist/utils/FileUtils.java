package com.myserieslist.utils;

public class FileUtils {

    public static String getExtension(byte[] file) {
        return TikaUtils.getInstance().detect(file);
    }
}
