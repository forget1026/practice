package com.forget1026.practice.client;

import com.forget1026.practice.dto.PracticeKakaoDTO;
import com.forget1026.practice.request.SearchQueryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "getKaKaoBlogData", url = "https://dapi.kakao.com")
public interface PracticeKakaoAPIClient {
    @GetMapping("/v2/search/blog")
    PracticeKakaoDTO getBlogData(@RequestHeader(value = "Authorization") String authorizationHeader,
                                 @SpringQueryMap SearchQueryRequest request);
}
