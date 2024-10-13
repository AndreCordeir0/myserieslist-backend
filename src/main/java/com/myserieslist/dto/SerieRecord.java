package com.myserieslist.dto;

import com.myserieslist.enums.SerieStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SerieRecord(
        Long id,
        @NotNull
        String name,
        SerieStatus status,
        String synopse,
        LocalDate airingDate,
        List<CategoryRecord> categories,
        List<ImageRecord> images
) {
}
