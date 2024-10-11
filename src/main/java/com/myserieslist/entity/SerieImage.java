package com.myserieslist.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "serie_image")
public class SerieImage extends PanacheEntityBase {

    @JoinColumn(name = "id_image")
    @ManyToOne
    @Id
    private Image image;

    @JoinColumn(name = "id_serie")
    @ManyToOne
    @Id
    private Serie serie;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
}
