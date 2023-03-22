package com.forget1026.practice.service;

import com.forget1026.practice.request.SearchQueryRequest;
import com.forget1026.practice.response.SearchQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PracticeRestServiceTest {
    @Autowired
    PracticeRestService service;

    @Test
    @DisplayName("조회수 락 관련 테스트")
    void checkLockTest() throws InterruptedException {
        SearchQueryRequest request = SearchQueryRequest.builder()
                .query("테스트")
                .build();
        int execute = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(execute);

        for (int i = 0; i < execute; i++) {
            executorService.execute(() -> {
                try {
                    service.getBlogData(request);
                    log.info("성공");
                } catch (ObjectOptimisticLockingFailureException oe) {
                    log.info("충돌");
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        List<SearchQueryResponse> searchQueryList = service.getSearchQueryList();
        log.info("result : " + searchQueryList.get(0).toString());

        assertThat(searchQueryList.get(0).getCount()).isEqualTo(execute);
    }
}