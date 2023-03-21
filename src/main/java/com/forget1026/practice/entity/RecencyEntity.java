package com.forget1026.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RecencyEntity {
    @Id
    private Long id;

    @OneToOne(mappedBy = "recency")
    BlogEntity blogEntity;

    @ManyToOne
    @JoinColumn(name = "practice_list_id")
    private PracticeList practiceList;
}
