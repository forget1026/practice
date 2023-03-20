package com.forget1026.practice.controller;

import com.forget1026.practice.dto.PracticeKakaoDTO;
import com.forget1026.practice.request.SearchQueryRequest;
import com.forget1026.practice.response.SearchQueryResponse;
import com.forget1026.practice.service.PracticeRestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PracticeRestController {
    private final PracticeRestService practiceRestService;

    @GetMapping("/search")
    public PracticeKakaoDTO getBlogData(@ModelAttribute SearchQueryRequest request) {
        return practiceRestService.getBlogData(request);
    }

    @GetMapping("/top10")
    public List<SearchQueryResponse> getSearchQueryList() {
        return practiceRestService.getSearchQueryList();
    }
}
