package com.myserieslist.utils;

import org.apache.tika.Tika;

public class TikaUtils {
    private static Tika singleInstance = null;
    public static synchronized Tika getInstance()
    {
        if (singleInstance == null)
            singleInstance = new Tika();
        return singleInstance;
    }

    private TikaUtils(){}
}
