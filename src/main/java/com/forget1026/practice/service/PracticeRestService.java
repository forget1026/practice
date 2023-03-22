package com.forget1026.practice.service;

import com.forget1026.practice.client.PracticeKakaoAPIClient;
import com.forget1026.practice.dto.PracticeKakaoDTO;
import com.forget1026.practice.entity.AccuracyEntity;
import com.forget1026.practice.entity.BlogEntity;
import com.forget1026.practice.entity.PracticeList;
import com.forget1026.practice.entity.RecencyEntity;
import com.forget1026.practice.enums.SortType;
import com.forget1026.practice.keys.APIKeys;
import com.forget1026.practice.mapper.BlogEntityMapper;
import com.forget1026.practice.repository.AccuracyRepository;
import com.forget1026.practice.repository.BlogRepository;
import com.forget1026.practice.repository.PracticeListRepository;
import com.forget1026.practice.repository.RecencyRepository;
import com.forget1026.practice.request.SearchQueryRequest;
import com.forget1026.practice.response.SearchQueryResponse;
import com.forget1026.practice.response.SearchQueryResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PracticeRestService {
   private final PracticeKakaoAPIClient kakaoAPIClient;
   private final PracticeListRepository practiceListRepository;
   private final BlogRepository blogRepository;
   private final AccuracyRepository accuracyRepository;
   private final RecencyRepository recencyRepository;
   private final APIKeys apiKeys;

    /**
     * 블로그 데이터 가져오기
     * @param request 요청 데이터
     * @return 블로그 데이터
     */
    public List<SearchQueryResultResponse> getBlogData(SearchQueryRequest request) {
        // 조회수 체크
        practiceListRepository.practiceListAddCount(request.getQuery());
        PracticeList byId = practiceListRepository.findById(request.getQuery()).orElseThrow(RuntimeException::new);
        // 아예 데이터가 없는 경우
        if (byId.getPageableCount() == -1) {
            return BlogEntityMapper.INSTANCE.blogEntityToSearchQueryResponse(insertBlogEntity(request, byId));
        }
        int start = byId.getPageableCount() - (request.getPage() - 1) * request.getSize();
        if (request.getSortType() == SortType.accuracy) {
            List<BlogEntity> blogEntitiesByAccuracy = blogRepository.findBlogEntitiesByQueryAndAccuracy(start, start - request.getSize(), request.getQuery());
            if (blogEntitiesByAccuracy.size() == request.getSize()) {
                return BlogEntityMapper.INSTANCE.blogEntityToSearchQueryResponse(blogEntitiesByAccuracy);
            }
            return BlogEntityMapper.INSTANCE.blogEntityToSearchQueryResponse(updateBlogEntityByAccuracy(request, byId));
        }
        List<BlogEntity> blogEntitiesByRecency = blogRepository.findBlogEntitiesByQueryAndRecency(start, start - request.getSize(), request.getQuery());
        if (blogEntitiesByRecency.size() == request.getSize()) {
            return BlogEntityMapper.INSTANCE.blogEntityToSearchQueryResponse(blogEntitiesByRecency);
        }
        return BlogEntityMapper.INSTANCE.blogEntityToSearchQueryResponse(updateBlogEntityByRecency(request, byId));
    }

    // insert 처리
    public List<BlogEntity> insertBlogEntity(SearchQueryRequest request,PracticeList byId) {
        // 데이터 받기
        PracticeKakaoDTO result = kakaoAPIClient.getBlogData(apiKeys.getKAKAO_KEY(), request);
        // 데이터 변환
        List<BlogEntity> blogEntities = BlogEntityMapper.INSTANCE.documentsToBlogEntity(result.getDocuments());
        // 관계를 맵핑한다.
        byId.setPageableCount(2500); // 최대 값. meta의 pageable_count 결과 값은 변하기에 의미가 없다. 50page, 50 size
        AtomicInteger start = new AtomicInteger(result.getMeta().getPageable_count() - request.getSize() * (request.getPage() - 1));
        if (request.getSortType() == SortType.accuracy) {
            List<AccuracyEntity> accuracyEntityList = new ArrayList<>();
            blogEntities.forEach(data -> {
                AccuracyEntity build = AccuracyEntity.builder()
                        .idx(start.getAndDecrement())
                        .build();
                data.setAccuracy(build);
                accuracyEntityList.add(build);
            });
            byId.setAccuracyEntityList(accuracyEntityList);
            blogRepository.saveAll(blogEntities);
            accuracyRepository.saveAll(accuracyEntityList);
        } else {
            List<RecencyEntity> recencyEntities = new ArrayList<>();
            blogEntities.forEach(data -> {
                RecencyEntity build = RecencyEntity.builder()
                        .id(start.getAndDecrement())
                        .build();
                data.setRecency(build);
                recencyEntities.add(build);
            });
            byId.setRecencyEntityList(recencyEntities);
            blogRepository.saveAll(blogEntities);
            recencyRepository.saveAll(recencyEntities);
        }
        practiceListRepository.save(byId);

        return blogEntities;
    }

    public List<BlogEntity> updateBlogEntityByAccuracy(SearchQueryRequest request, PracticeList byId) {
        List<BlogEntity> insertData = checkData(request);
        int start = byId.getPageableCount() - request.getSize() * (request.getPage() - 1);
        int end = Math.max(start - request.getSize(), 0);
        List<AccuracyEntity> insertAccuracy = new ArrayList<>();
        Map<Integer, AccuracyEntity> collect = accuracyRepository.findAccuracyEntitiesByIdxBetweenOrderByIdxDesc(end, start, request.getQuery())
                .stream().collect(Collectors.toMap(AccuracyEntity::getIdx, Function.identity()));
        // accuracy 관련 수정
        for (int i = start; i > end; i--) {
            BlogEntity blogEntity = insertData.get(start - i);
            if (collect.containsKey(i)) {
                blogEntity.setAccuracy(collect.get(i));
            } else {
                AccuracyEntity accuracy = AccuracyEntity.builder()
                        .idx(i)
                        .build();
                blogEntity.setAccuracy(accuracy);
                accuracy.setPracticeList(byId);
                insertAccuracy.add(accuracy);
            }
        }
        // 저장
        blogRepository.saveAll(insertData);
        accuracyRepository.saveAll(insertAccuracy);
        practiceListRepository.save(byId);

        return insertData;
    }

    public List<BlogEntity> updateBlogEntityByRecency(SearchQueryRequest request, PracticeList byId) {
        // 넣어야 되는 데이터 추리기
        List<BlogEntity> insertData = checkData(request);

        int start = byId.getPageableCount() - request.getSize() * (request.getPage() - 1);
        int end = Math.max(start - request.getSize(), 0);
        List<RecencyEntity> insertRecency = new ArrayList<>();
        Map<Integer, RecencyEntity> collect = recencyRepository.findRecencyEntitiesByIdxBetweenOrderByIdxDesc(end, start, request.getQuery())
                .stream().collect(Collectors.toMap(RecencyEntity::getIdx, Function.identity()));
        for (int i = start; i > end; i--) {
            BlogEntity blogEntity = insertData.get(start - i);
            if (collect.containsKey(i)) {
                blogEntity.setRecency(collect.get(i));
            } else {
                RecencyEntity recency = RecencyEntity.builder()
                        .idx(i)
                        .build();
                blogEntity.setRecency(recency);
                recency.setPracticeList(byId);
                insertRecency.add(recency);
            }
        }
        // 저장
        blogRepository.saveAll(insertData);
        recencyRepository.saveAll(insertRecency);
        practiceListRepository.save(byId);

        return insertData;
    }

    List<BlogEntity> checkData(SearchQueryRequest request) {
        // 데이터 받기
        // 주의 pageable_count - 이거 처음에 비하면 점점 줄어듬.
        PracticeKakaoDTO result = kakaoAPIClient.getBlogData(apiKeys.getKAKAO_KEY(), request);
        // 데이터 변환
        List<BlogEntity> blogEntities = BlogEntityMapper.INSTANCE.documentsToBlogEntity(result.getDocuments());

        List<BlogEntity> insertData = new ArrayList<>();
        for (BlogEntity blogEntity : blogEntities) {
            Optional<BlogEntity> blogEntitiesByUrl = blogRepository.findBlogEntitiesByUrl(blogEntity.getUrl());
            if (blogEntitiesByUrl.isPresent()) {
                insertData.add(blogEntitiesByUrl.get());
            } else {
                insertData.add(blogEntity);
            }
        }

        return insertData;
    }

    public List<SearchQueryResponse> getSearchQueryList() {
        return practiceListRepository.findTop10ByOrderByCountDesc()
                .stream()
                .map(SearchQueryResponse::new)
                .collect(Collectors.toList());
    }
}
