package com.forget1026.practice.repository;

import com.forget1026.practice.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    @Query(value = "select * from blog_entity b " +
            "left join accuracy_entity a on a.id = b.accuracy_id " +
            "where a.id <= :start and a.id >= :end", nativeQuery = true)
    List<BlogEntity> findBlogEntitiesByAccuracy(int start, int end);
}
