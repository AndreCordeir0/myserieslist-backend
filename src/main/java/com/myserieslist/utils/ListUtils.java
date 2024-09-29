package com.myserieslist.utils;

import java.util.List;

public class ListUtils {

    public static Boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static Boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
}
