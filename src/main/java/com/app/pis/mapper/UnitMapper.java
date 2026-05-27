package com.app.pis.mapper;

import com.app.pis.dto.request.UnitRequest;
import com.app.pis.dto.response.UnitResponse;
import com.app.pis.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnitMapper {

    UnitResponse toResponse (Unit unit);

    @Mapping(target = "id", ignore = true)
    Unit toEntity (UnitRequest request);





}
