package ru.snapgot.coolhairstyle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Record")
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User barber;
    @ManyToOne
    private User client;
    @OneToOne
    private Service service;
    @Enumerated(value = EnumType.STRING)
    private RecordStatus recordStatus;

    public Record(User barber, User client, Service service, RecordStatus recordStatus) {
        this.barber = barber;
        this.client = client;
        this.service = service;
        this.recordStatus = recordStatus;
    }
}
