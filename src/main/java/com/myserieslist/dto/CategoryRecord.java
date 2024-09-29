package com.myserieslist.dto;

import com.myserieslist.entity.Category;
import com.myserieslist.enums.SerieStatus;

import java.time.LocalDate;
import java.util.List;

public record CategoryRecord(
        Long id,
        String name
) {
}
