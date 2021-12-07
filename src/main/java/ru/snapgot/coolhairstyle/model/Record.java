package ru.snapgot.coolhairstyle.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Barber barber;
    @ManyToOne
    private Client client;
    @OneToOne
    private Service service;
    private boolean accepted;
}
