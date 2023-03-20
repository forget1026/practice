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
public class AccuracyEntity extends JpaBaseEntity {
    @Id
    private Long id;

    @OneToOne(mappedBy = "accuracy")
    BlogEntity blogEntity;
}
