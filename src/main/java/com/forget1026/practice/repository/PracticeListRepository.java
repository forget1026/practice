package com.forget1026.practice.repository;

import com.forget1026.practice.entity.PracticeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PracticeListRepository extends JpaRepository<PracticeList, String> {
    List<PracticeList> findTop10ByOrderByCountDesc();

    @Modifying
    @Transactional
    @Query(value = "insert into practice_list (query, count, pageable_count) values(:query, 1, -1) ON DUPLICATE KEY UPDATE count = count + 1",
            nativeQuery = true)
    void practiceListAddCount(String query);
}
