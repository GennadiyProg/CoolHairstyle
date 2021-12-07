package ru.snapgot.coolhairstyle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Barber")
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String photoUrl;
    private String city;
    @OneToMany
    private List<Service> services;
    @OneToMany
    private List<Record> records;
    @OneToMany
    private List<Comment> comments;
}
