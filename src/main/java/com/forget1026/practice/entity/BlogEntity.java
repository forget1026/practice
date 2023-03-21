package com.forget1026.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;               // 블로그 글 제목

    @Column(columnDefinition = "TEXT")
    private String contents;            // 블로그 글 요약

    @Column(unique = true)
    private String url;                 // 블로그 글 URL

    private String blogName;            // 블로그의 이름

    private String thumbNail;           // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음

    private ZonedDateTime datetime;     // 블로그 글 작성시간

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "accuracy_id")
    private AccuracyEntity accuracy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recency_id")
    private RecencyEntity recency;

    public void setAccuracy(AccuracyEntity accuracyEntity) {
        this.accuracy = accuracyEntity;
        accuracyEntity.setBlogEntity(this);
    }

    public void setRecency(RecencyEntity recencyEntity) {
        this.recency = recencyEntity;
        recencyEntity.setBlogEntity(this);
    }
}
