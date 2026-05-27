package com.app.pis.mapper;

import com.app.pis.dto.request.CustomerGroupRequest;
import com.app.pis.dto.response.CustomerGroupResponse;
import com.app.pis.entity.CustomerGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerGroupMapper {
    CustomerGroupResponse toResponse(CustomerGroup group);

    @Mapping(target = "id", ignore = true)
    CustomerGroup toEntity(CustomerGroupRequest request);
}
