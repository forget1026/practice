package com.forget1026.practice.entity;

import com.forget1026.practice.repository.PracticeListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PracticeListTest {
    @Autowired
    PracticeListRepository practiceListRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void jpaAuditingTest() throws Exception {
        String query = "이효리";
        Long count = 1L;

        PracticeList practiceList = PracticeList.createQuery(query, count);

        practiceListRepository.save(practiceList);
    }

}