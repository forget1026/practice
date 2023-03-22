package com.forget1026.practice.repository;

import com.forget1026.practice.entity.RecencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecencyRepository extends JpaRepository<RecencyEntity, Integer> {
    @Query(value = "select * from recency_entity a " +
            "left join practice_list t on t.query = a.practice_list_id " +
            "where t.query = :query and " +
            " a.idx <= :start and a.idx > :end order by a.idx desc", nativeQuery = true)
    List<RecencyEntity> findRecencyEntitiesByIdxBetweenOrderByIdxDesc(int end, int start, String query);
}
