package ru.snapgot.coolhairstyle.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Barber barber;
    private String name;
    private int price;
}
