package com.entity;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class NamedEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    private String uuid;
}
