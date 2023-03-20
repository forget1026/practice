package com.forget1026.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;               // 블로그 글 제목

    private String contents;            // 블로그 글 요약

    @Column(unique = true)
    private String url;                 // 블로그 글 URL

    private String blogName;            // 블로그의 이름

    private String thumbNail;           // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음

    private ZonedDateTime datetime;     // 블로그 글 작성시간

    @OneToOne
    @JoinColumn(name = "accuracy_id")
    private AccuracyEntity accuracy;

    @OneToOne
    @JoinColumn(name = "recency_id")
    private RecencyEntity recency;

    @ManyToOne
    @JoinColumn(name = "practice_list_id")
    private PracticeList practiceList;
}
