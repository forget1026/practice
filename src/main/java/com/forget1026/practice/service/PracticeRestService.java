package com.forget1026.practice.service;

import com.forget1026.practice.client.PracticeKakaoAPIClient;
import com.forget1026.practice.dto.PracticeKakaoDTO;
import com.forget1026.practice.entity.AccuracyEntity;
import com.forget1026.practice.entity.BlogEntity;
import com.forget1026.practice.entity.PracticeList;
import com.forget1026.practice.enums.SortType;
import com.forget1026.practice.keys.APIKeys;
import com.forget1026.practice.mapper.BlogEntityMapper;
import com.forget1026.practice.repository.AccuracyRepository;
import com.forget1026.practice.repository.BlogRepository;
import com.forget1026.practice.repository.PracticeListRepository;
import com.forget1026.practice.request.SearchQueryRequest;
import com.forget1026.practice.response.SearchQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PracticeRestService {
   private final PracticeKakaoAPIClient kakaoAPIClient;
   private final PracticeListRepository practiceListRepository;
   private final BlogRepository blogRepository;
   private final AccuracyRepository accuracyRepository;
   private final APIKeys apiKeys;

    public List<BlogEntity> getBlogData(SearchQueryRequest request) {
        // 조회수 체크
        practiceListRepository.practiceListAddCount(request.getQuery());
        PracticeList byId = practiceListRepository.findById(request.getQuery()).orElseThrow(RuntimeException::new);
        // 아예 데이터가 없는 경우
        if (byId.getPageableCount() == -1) {
            return insertBlogEntity(request, byId);
        }
        int start = byId.getPageableCount() - (request.getPage() - 1) * request.getSize();

        List<BlogEntity> blogEntitiesByAccuracy = blogRepository.findBlogEntitiesByAccuracy(start, start - request.getSize());
        if (blogEntitiesByAccuracy.size() == request.getSize()) {
            return blogEntitiesByAccuracy;
        }

        return updateBlogEntity(request, byId);
    }

    // insert 처리
    private List<BlogEntity> insertBlogEntity(SearchQueryRequest request,PracticeList byId) {
        // 데이터 받기
        PracticeKakaoDTO result = kakaoAPIClient.getBlogData(apiKeys.getKAKAO_KEY(), request);
        // 데이터 변환
        List<BlogEntity> blogEntities = BlogEntityMapper.INSTANCE.DocumentsToBlogEntity(result.getDocuments());
        // 관계를 맵핑한다.
        byId.setPageableCount(result.getMeta().getPageable_count());
        if (request.getSortType() == SortType.accuracy) {
            AtomicInteger start = new AtomicInteger(result.getMeta().getPageable_count() - request.getSize() * (request.getPage() - 1));
            List<AccuracyEntity> accuracyEntityList = new ArrayList<>();
            blogEntities.forEach(data -> {
                AccuracyEntity build = AccuracyEntity.builder()
                        .id(start.getAndDecrement())
                        .build();
                data.setAccuracy(build);
                accuracyEntityList.add(build);
            });
            byId.setAccuracyEntityList(accuracyEntityList);
            blogRepository.saveAll(blogEntities);
            accuracyRepository.saveAll(accuracyEntityList);
            practiceListRepository.save(byId);
        }
        return blogEntities;
    }

    private List<BlogEntity> updateBlogEntity(SearchQueryRequest request,PracticeList byId) {
        // 데이터 받기
        PracticeKakaoDTO result = kakaoAPIClient.getBlogData(apiKeys.getKAKAO_KEY(), request);
        // 데이터 변환
        List<BlogEntity> blogEntities = BlogEntityMapper.INSTANCE.DocumentsToBlogEntity(result.getDocuments());
        blogEntities.forEach(data -> {
        });
        if (request.getSortType() == SortType.accuracy) {

        }


        return blogEntities;
    }

    public List<SearchQueryResponse> getSearchQueryList() {
        return practiceListRepository.findTop10ByOrderByCountDesc()
                .stream()
                .map(SearchQueryResponse::new)
                .collect(Collectors.toList());
    }
}
