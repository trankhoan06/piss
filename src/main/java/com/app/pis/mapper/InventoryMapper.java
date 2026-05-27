package com.app.pis.mapper;

import com.app.pis.dto.response.InventoryResponse;
import com.app.pis.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    @Mapping(target = "medicineId", source = "medicine.id")
    @Mapping(target = "supplierId", source = "supplier.id")
    InventoryResponse toResponse(Inventory inventory);
}
