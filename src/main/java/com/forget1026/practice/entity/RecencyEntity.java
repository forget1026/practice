package com.forget1026.practice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecencyEntity {
    @Id
    private Integer id;

    @OneToOne(mappedBy = "recency")
    BlogEntity blogEntity;

    @ManyToOne
    @JoinColumn(name = "practice_list_id")
    private PracticeList practiceList;
}
