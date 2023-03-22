package com.forget1026.practice.mapper;

import com.forget1026.practice.dto.Documents;
import com.forget1026.practice.entity.BlogEntity;
import com.forget1026.practice.response.SearchQueryResponse;
import com.forget1026.practice.response.SearchQueryResultResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlogEntityMapper {
    BlogEntityMapper INSTANCE = Mappers.getMapper(BlogEntityMapper.class);

    List<BlogEntity> documentsToBlogEntity(List<Documents> documents);

    BlogEntity documentToBlogEntity(Documents documents);

    List<SearchQueryResultResponse> blogEntityToSearchQueryResponse(List<BlogEntity> blogEntityList);
}
