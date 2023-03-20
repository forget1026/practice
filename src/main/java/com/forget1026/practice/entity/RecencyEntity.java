package com.forget1026.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RecencyEntity {
    @Id
    private Long id;

    @OneToOne(mappedBy = "recency")
    BlogEntity blogEntity;
}
