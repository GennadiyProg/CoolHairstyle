package ru.snapgot.coolhairstyle.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Barber barber;
    @ManyToOne
    private Client client;
    private String description;
}
