package com.forget1026.practice.repository;

import com.forget1026.practice.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    @Query(value = "select * from blog_entity b " +
            "left join accuracy_entity a on a.blog_entity_id = b.id " +
            "left join practice_list t on t.query = a.practice_list_id " +
            "where t.query = :query and " +
            " a.idx <= :start and a.idx > :end order by a.idx desc", nativeQuery = true)
    List<BlogEntity> findBlogEntitiesByQueryAndAccuracy(int start, int end, String query);

    @Query(value = "select * from blog_entity b " +
            "left join recency_entity a on a.blog_entity_id = b.id " +
            "left join practice_list t on t.query = a.practice_list_id " +
            "where t.query = :query and " +
            " a.idx <= :start and a.idx > :end order by a.idx desc", nativeQuery = true)
    List<BlogEntity> findBlogEntitiesByQueryAndRecency(int start, int end, String query);
    Optional<BlogEntity> findBlogEntitiesByUrl(String url);
}
