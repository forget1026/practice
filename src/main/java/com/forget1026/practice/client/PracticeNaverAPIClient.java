package com.forget1026.practice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "getNaverBlogData", url = "https://dapi.kakao.com")
public interface PracticeNaverAPIClient {
}
