package com.myserieslist.services.impl;

import com.myserieslist.dto.CategoryRecord;
import com.myserieslist.dto.Pagination;
import com.myserieslist.dto.SerieRecord;
import com.myserieslist.entity.Category;
import com.myserieslist.entity.Serie;
import com.myserieslist.entity.SerieCategory;
import com.myserieslist.exceptions.MySeriesListException;
import com.myserieslist.services.SerieService;
import com.myserieslist.utils.ListUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.util.List;


@RequestScoped
public class SerieServiceImpl implements SerieService {

    @Override
    @Transactional
    public Long save(SerieRecord serieRecord) {
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
        return serie.getId();
    }

    private void addCategorieSeries(List<CategoryRecord> categories, Long serieId) {
        if (ListUtils.isNotEmpty(categories)) {
            for (CategoryRecord categoryRecord : categories) {
                SerieCategory serieCategory = new SerieCategory();
                serieCategory.setCategory(new Category(categoryRecord.id()));
                serieCategory.setSerie(new Serie(serieId));
                serieCategory.persist();
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
        return null;
    }

    @Override
    @Transactional
    public void remove(Long id) {

    }
}
