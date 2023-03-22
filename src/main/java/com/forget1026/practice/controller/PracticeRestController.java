package com.forget1026.practice.controller;

import com.forget1026.practice.request.SearchQueryRequest;
import com.forget1026.practice.response.SearchQueryResponse;
import com.forget1026.practice.response.SearchQueryResultResponse;
import com.forget1026.practice.service.PracticeRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class PracticeRestController {
    private final PracticeRestService practiceRestService;

    @Operation(summary = "검색 요청", description = "카카오 블로그 데이터 검색", tags = { "PracticeRestController Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SearchQueryResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/search")
    public List<SearchQueryResultResponse> getBlogData(@ModelAttribute @Valid SearchQueryRequest request) {
        return practiceRestService.getBlogData(request);
    }

    @GetMapping("/top10")
    @Operation(summary = "검색 순위", description = "카카오 블로그 데이터 검색 순위", tags = { "PracticeRestController Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SearchQueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    public List<SearchQueryResponse> getSearchQueryList() {
        return practiceRestService.getSearchQueryList();
    }
}
