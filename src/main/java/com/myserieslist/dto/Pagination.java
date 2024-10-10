package com.myserieslist.dto;

import java.util.List;
import java.util.Map;

public record Pagination<T>(
        Integer pageNumber,
        Integer recordsPerPage,
        Map<String, String> filters,
        List<T> results,
        Long totalResults
 ) {
}
