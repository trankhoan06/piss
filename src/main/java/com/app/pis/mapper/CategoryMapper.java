package com.app.pis.mapper;

import com.app.pis.dto.request.CategoryRequest;
import com.app.pis.dto.response.CategoryResponse;
import com.app.pis.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryResponse toResponse (Category category);

    @Mapping(target = "id", ignore = true)
    Category toEntity (CategoryRequest request);


}
