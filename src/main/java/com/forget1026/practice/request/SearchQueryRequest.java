package com.forget1026.practice.request;

import com.forget1026.practice.enums.SortType;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryRequest {
    @NotEmpty
    String query;
    @Builder.Default
    SortType sortType = SortType.accuracy;
    @Builder.Default
    Integer page = 1;
    @Builder.Default
    Integer size = 10;
}
