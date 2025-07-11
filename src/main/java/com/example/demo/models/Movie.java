package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class Movie extends BaseModel{

    private String name;

    @OneToMany
    private List<Actor> actors;


    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Feature> features;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Genre> genre;
}
