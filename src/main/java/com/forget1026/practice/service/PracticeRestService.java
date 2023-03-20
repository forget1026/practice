package com.forget1026.practice.service;

import com.forget1026.practice.client.PracticeKakaoAPIClient;
import com.forget1026.practice.dto.PracticeKakaoDTO;
import com.forget1026.practice.keys.APIKeys;
import com.forget1026.practice.repository.PracticeListRepository;
import com.forget1026.practice.request.SearchQueryRequest;
import com.forget1026.practice.response.SearchQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PracticeRestService {
   private final PracticeKakaoAPIClient kakaoAPIClient;
   private final PracticeListRepository practiceListRepository;
   private final APIKeys apiKeys;

    public PracticeKakaoDTO getBlogData(SearchQueryRequest request) {
        practiceListRepository.practiceListAddCount(request.getQuery()); // 이건 단순 조회수 카운팅

        return kakaoAPIClient.getBlogData(apiKeys.getKAKAO_KEY(), request);
    }

    public List<SearchQueryResponse> getSearchQueryList() {
        return practiceListRepository.findTop10ByOrderByCountDesc()
                .stream()
                .map(SearchQueryResponse::new)
                .collect(Collectors.toList());
    }
}
