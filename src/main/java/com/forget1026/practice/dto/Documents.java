package com.forget1026.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Documents {
    String title;               // 블로그 글 제목
    String contents;            // 블로그 글 요약
    String url;                 // 블로그 글 URL
    @JsonProperty(value = "blogname")
    String blogName;            // 블로그의 이름
    @JsonProperty(value = "thumbnail")
    String thumbNail;           // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
    ZonedDateTime datetime;     // 블로그 글 작성시간
}