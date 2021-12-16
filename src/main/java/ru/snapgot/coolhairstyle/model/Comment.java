package ru.snapgot.coolhairstyle.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User barber;
    @ManyToOne
    private User client;
    private String description;

    public Comment(User barber, User client, String description) {
        this.barber = barber;
        this.client = client;
        this.description = description;
    }
}
