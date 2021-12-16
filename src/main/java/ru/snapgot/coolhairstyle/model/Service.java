package ru.snapgot.coolhairstyle.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Service")
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User barber;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int price;

    public Service(User barber, String name, String description, int price) {
        this.barber = barber;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
