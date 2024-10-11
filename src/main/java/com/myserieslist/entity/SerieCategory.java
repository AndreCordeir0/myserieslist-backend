package com.myserieslist.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "serie_category")
public class SerieCategory extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "SERIE_CATEGORY_serie_category_id_seq", sequenceName = "\"SERIE_CATEGORY_serie_category_id_seq\"", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERIE_CATEGORY_serie_category_id_seq")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serie_category_id")
    private Long id;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @JoinColumn(name = "serie_id")
    @ManyToOne
    private Serie serie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
}
