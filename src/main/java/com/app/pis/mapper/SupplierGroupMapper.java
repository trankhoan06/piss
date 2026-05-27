package com.app.pis.mapper;

import com.app.pis.dto.request.SupplierGroupRequest;
import com.app.pis.dto.response.SupplierGroupResponse;
import com.app.pis.entity.SupplierGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierGroupMapper {
    SupplierGroupResponse toResponse(SupplierGroup group);

    @Mapping(target = "id", ignore = true)
    SupplierGroup toEntity(SupplierGroupRequest request);
}
