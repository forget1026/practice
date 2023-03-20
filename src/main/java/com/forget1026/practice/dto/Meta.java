package com.forget1026.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    Integer total_count;        // 검색된 문서 수
    Integer pageable_count;     // total_count 중 노출 가능 문서 수
    Boolean is_end;             // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
}
