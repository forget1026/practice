package com.forget1026.practice.response;

import com.forget1026.practice.entity.PracticeList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryResponse {
    private String query;
    private Long count;

    public SearchQueryResponse(PracticeList practiceList) {
        this.query = practiceList.getId();
        this.count = practiceList.getCount();
    }
}
