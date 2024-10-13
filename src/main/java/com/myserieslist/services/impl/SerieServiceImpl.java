package com.myserieslist.services.impl;

import com.myserieslist.dto.CategoryRecord;
import com.myserieslist.dto.ImageRecord;
import com.myserieslist.dto.Pagination;
import com.myserieslist.dto.SerieRecord;
import com.myserieslist.entity.*;
import com.myserieslist.exceptions.MySeriesListException;
import com.myserieslist.services.ImageService;
import com.myserieslist.services.SerieService;
import com.myserieslist.utils.ListUtils;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@RequestScoped
public class SerieServiceImpl implements SerieService {

    @Inject
    ImageService imageService;

    @Override
    @Transactional
    public Long save(SerieRecord serieRecord) throws IOException {
        if (serieRecord.id() != null) {
            throw new MySeriesListException("Serie cannot have 'id' in persist", 400);
        }


        Serie serie = new Serie()
            .setName(serieRecord.name())
            .setStatus(serieRecord.status())
            .setAiringDate(serieRecord.airingDate())
            .setSynopse(serieRecord.synopse());

        serie.persist();
        addCategorieSeries(serieRecord.categories(), serie.getId());
        addImageSeries(serieRecord.images(), serie);
        return serie.getId();
    }

    private void addCategorieSeries(List<CategoryRecord> categories, Long idSerie) {
        if (ListUtils.isNotEmpty(categories)) {
            for (CategoryRecord categoryRecord : categories) {
                SerieCategory serieCategory = new SerieCategory();
                serieCategory.setCategory(new Category(categoryRecord.id()));
                serieCategory.setSerie(new Serie(idSerie));
                serieCategory.persist();
            }
        }
    }

    private void addImageSeries(List<ImageRecord> images, Serie serie) throws IOException {
        if (ListUtils.isNotEmpty(images)) {
            List<Image> imagesEntity = imageService.addImages(images);
            for (Image image: imagesEntity) {
                SerieImage serieImage = new SerieImage();
                serieImage.setImage(image);
                serieImage.setSerie(serie);
                serieImage.persist();
            }
        }
    }

    @Override
    @Transactional
    public Long update(SerieRecord serieRecord) {
        return null;
    }

    @Override
    public SerieRecord getById(Long id) {
        return null;
    }

    @Override
    public Pagination<SerieRecord> paginated(Pagination<SerieRecord> pagination) {
        PanacheQuery<Serie> series = Serie.find("name = :name", (HashMap) pagination.filters());
        List<Serie> result = series.page(pagination.pageNumber(), pagination.recordsPerPage()).list();
        List<SerieRecord> serieRecords = result.stream().map(serie ->
                new SerieRecord(
                        serie.getId(),
                        serie.getName(),
                        serie.getStatus(),
                        serie.getSynopse(),
                        serie.getAiringDate(),
                        serie.getCategories().stream()
                                .map(category -> new CategoryRecord(category.getId(), category.getName()))
                                .toList(),
                        serie.getImages().stream()
                                .map(image->new ImageRecord(null, image.getDescImage(), image.getHash()))
                                .toList()
                )
        ).toList();
        Long count = series.count();
        return new Pagination<>(
                pagination.pageNumber(),
                pagination.recordsPerPage(),
                pagination.filters(),
                serieRecords,
                count
        );
    }

    @Override
    @Transactional
    public void remove(Long id) {

    }
}
