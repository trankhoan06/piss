package com.app.pis.mapper;

import com.app.pis.dto.request.CustomerRequest;
import com.app.pis.dto.response.CustomerResponse;
import com.app.pis.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    @Mapping(target = "customerGroupId", source = "customerGroup.id")
    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerGroup", ignore = true) // Will map manually in service
    Customer toEntity(CustomerRequest request);
}
