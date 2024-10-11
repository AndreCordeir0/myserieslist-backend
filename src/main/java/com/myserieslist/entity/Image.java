package com.myserieslist.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "image")
public class Image extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "IMAGE_id_image_seq", sequenceName = "\"IMAGE_id_image_seq\"", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_id_image_seq")
    @Column(name = "id_image")
    private Long id;

    @Column(name = "desc_image")
    private String descImage;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "hash")
    private String hash;

    public Image() {}

    public Image(Long id, String descImage, boolean active, LocalDateTime createdAt, String hash) {
        this.id = id;
        this.descImage = descImage;
        this.active = active;
        this.createdAt = createdAt;
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescImage() {
        return descImage;
    }

    public void setDescImage(String descImage) {
        this.descImage = descImage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
