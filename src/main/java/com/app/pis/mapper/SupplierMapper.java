package com.app.pis.mapper;

import com.app.pis.dto.request.SupplierRequest;
import com.app.pis.dto.response.SupplierResponse;
import com.app.pis.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {

    SupplierResponse toResponse (Supplier supplier);

    @Mapping(target = "id", ignore = true)
    Supplier toEntity (SupplierRequest request);



}
