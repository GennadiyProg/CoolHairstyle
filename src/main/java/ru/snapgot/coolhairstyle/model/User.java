package ru.snapgot.coolhairstyle.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String name;
    private String photoUrl;
    private String city;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String username, String password, String name, String photoUrl, String city, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.photoUrl = photoUrl;
        this.city = city;
        this.role = role;
    }

    public User(String name, String photoUrl, String city, Role role) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.city = city;
        this.role = role;
    }
}
