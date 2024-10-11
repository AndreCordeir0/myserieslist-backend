package com.myserieslist.entity;


import com.myserieslist.enums.UserSerieStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "user_serie_relationship")
public class UserSerie {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_serie_relationship_id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserSerieStatus status;

    @Column(name = "score")
    private Integer score;

    @Column(name = "episodes_completed")
    private Integer episodesCompleted;

    @JoinColumn(name = "serie_id")
    @ManyToOne
    private Serie serie;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;


}
