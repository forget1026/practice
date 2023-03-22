package com.forget1026.practice.mapper;

import com.forget1026.practice.dto.Documents;
import com.forget1026.practice.entity.BlogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlogEntityMapper {
    BlogEntityMapper INSTANCE = Mappers.getMapper(BlogEntityMapper.class);

    List<BlogEntity> DocumentsToBlogEntity(List<Documents> documents);

    BlogEntity DocumentToBlogEntity(Documents documents);
}
