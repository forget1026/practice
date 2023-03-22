package com.forget1026.practice.response;

import com.forget1026.practice.entity.PracticeList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryResponse {
    @Schema(example = "테스트")
    private String query;
    @Schema(example = "11")
    private Long count;

    public SearchQueryResponse(PracticeList practiceList) {
        this.query = practiceList.getId();
        this.count = practiceList.getCount();
    }
}
