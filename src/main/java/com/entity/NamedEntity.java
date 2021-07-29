package com.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class NamedEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    private String uuid;
}
