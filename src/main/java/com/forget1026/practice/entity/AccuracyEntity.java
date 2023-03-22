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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer idx;

    @ManyToOne
    @JoinColumn(name = "blog_entity_id")
    BlogEntity blogEntity;

    @ManyToOne
    @JoinColumn(name = "practice_list_id")
    private PracticeList practiceList;
}
