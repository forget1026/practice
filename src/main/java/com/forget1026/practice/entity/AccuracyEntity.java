package com.forget1026.practice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccuracyEntity extends JpaBaseEntity {
    @Id
    private Integer id;

    @OneToOne(mappedBy = "accuracy")
    BlogEntity blogEntity;

    @ManyToOne
    @JoinColumn(name = "practice_list_id")
    private PracticeList practiceList;
}
