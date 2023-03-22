package com.forget1026.practice.repository;

import com.forget1026.practice.entity.AccuracyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccuracyRepository extends JpaRepository<AccuracyEntity, Integer> {
    @Query(value = "select * from accuracy_entity a " +
            "left join practice_list t on t.query = a.practice_list_id " +
            "where t.query = :query and " +
            " a.idx <= :start and a.idx > :end order by a.idx desc", nativeQuery = true)
    List<AccuracyEntity> findAccuracyEntitiesByIdxBetweenOrderByIdxDesc(int end, int start, String query);
}
