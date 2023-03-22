package com.forget1026.practice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryResultResponse {
    @Schema(example = "NBTI <b>테스트</b> 무료<b>테스트</b> (바로가기)")
    private String title;               // 블로그 글 제목
    @Schema(example = "나 자신도 몰랐던 내 성격 알아보기 NBTI <b>테스트</b>에 대하여 알아보겠습니다. 안녕하세요~ 요즘 핫한 심리<b>테스트</b>죠!! 바로 NBTI <b>테스트</b>입니다. 나도 몰랐던 나의 성격을 알아볼 수 있어서 많은 분들이 nbti <b>테스트</b>를 합니다. 그럼 이번 시간에는 nbti <b>테스트</b> 무료검사를 할 수 있는 방법을 소개해보도로 하겠습니다. MBTI...")
    private String contents;            // 블로그 글 요약
    @Schema(example = "http://fi.bibomcoco.com/19")
    private String url;                 // 블로그 글 URL
    @Schema(example = "엠오엠")
    private String blogName;            // 블로그의 이름
    @Schema(example = "https://search3.kakaocdn.net/argon/130x130_85_c/GcYNFJ36CBz")
    private String thumbNail;           // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
    @Schema(example = "2023-03-16T04:25:51Z")
    private ZonedDateTime datetime;     // 블로그 글 작성시간
}
