package com.forget1026.practice.repository;

import com.forget1026.practice.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    @Query(value = "select * from blog_entity b " +
            "left join accuracy_entity a on a.id = b.accuracy_id " +
            "where a.id <= :start and a.id >= :end order by b.accuracy_id desc", nativeQuery = true)
    List<BlogEntity> findBlogEntitiesByAccuracy(int start, int end);

    @Query(value = "select * from blog_entity b " +
            "left join recency_entity a on a.id = b.recency_id " +
            "where a.id <= :start and a.id >= :end order by b.recency_id desc", nativeQuery = true)
    List<BlogEntity> findBlogEntitiesByRecency(int start, int end);
}
