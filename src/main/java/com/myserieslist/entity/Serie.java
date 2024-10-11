package com.myserieslist.entity;

import com.myserieslist.dto.CategoryRecord;
import com.myserieslist.enums.SerieStatus;
import com.myserieslist.utils.ListUtils;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "serie")
public class Serie extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "SERIE_serie_id_seq", sequenceName = "\"SERIE_serie_id_seq\"", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERIE_serie_id_seq")
    @Column(name = "serie_id")
    private Long id;

    @Column(name = "serie_name")
    private String name;

    @Column(name = "serie_status")
    @Enumerated(EnumType.STRING)
    private SerieStatus status;

    @Column(name = "serie_synopse")
    private String synopse;

    @Column(name = "serie_airing_date")
    private LocalDate airingDate;

    @JoinTable(
            name = "SERIE_CATEGORY",
            joinColumns = @JoinColumn(
                    name = "serie_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id"
            )
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;

    @JoinTable(
            name = "SERIE_IMAGE",
            joinColumns = @JoinColumn(
                    name = "id_serie"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "id_image"
            )
    )
    @ManyToMany
    private List<Image> images;


    public Serie() {
    }

    public Serie(Long serieId) {
        this.id = serieId;
    }

    public List<Category> getCategories() {
        if (ListUtils.isEmpty(categories)) {
            this.categories = new ArrayList<>();
        }
        return this.categories;
    }

    public Serie setCategories(List<Category> categoryes) {
        this.categories = categoryes;
        return this;
    }

    public Serie insertCategoryRecords(List<CategoryRecord> categoryesRecord) {
        if (ListUtils.isNotEmpty(categoryesRecord)) {
            for (CategoryRecord category: categoryesRecord) {
                getCategories().add(new Category()
                        .setId(category.id())
                        .setName(category.name()));
            }
        }
        return this;
    }

    public Serie(List<Category> categoryes) {
        this.categories = categoryes;
    }
    public Serie(Long id, String name, SerieStatus status, String synopse, LocalDate airingDate, List<Category> categoryes) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.synopse = synopse;
        this.airingDate = airingDate;
        this.categories = categoryes;
    }

    public Long getId() {
        return id;
    }

    public Serie setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Serie setName(String name) {
        this.name = name;
        return this;
    }

    public SerieStatus getStatus() {
        return status;
    }

    public Serie setStatus(SerieStatus status) {
        this.status = status;
        return this;
    }

    public String getSynopse() {
        return synopse;
    }

    public Serie setSynopse(String synopse) {
        this.synopse = synopse;
        return this;
    }

    public LocalDate getAiringDate() {
        return airingDate;
    }

    public Serie setAiringDate(LocalDate airingDate) {
        this.airingDate = airingDate;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
