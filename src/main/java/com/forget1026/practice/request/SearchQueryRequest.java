package com.forget1026.practice.request;

import com.forget1026.practice.enums.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryRequest {
    @Schema(required = true, example = "테스트")
    @NotEmpty
    String query;
    @Schema(example = "accuracy")
    @Builder.Default
    SortType sortType = SortType.accuracy;
    @Builder.Default
    @Schema(example = "1")
    @Min(1)
    @Max(50)
    Integer page = 1;
    @Builder.Default
    @Schema(example = "50")
    @Min(1)
    @Max(50)
    Integer size = 10;
}
