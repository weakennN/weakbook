package com.weakennN.weakbook.entity;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
