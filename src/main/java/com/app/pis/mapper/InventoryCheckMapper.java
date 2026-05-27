package com.app.pis.mapper;

import com.app.pis.dto.request.InventoryCheckDetailRequest;
import com.app.pis.dto.request.InventoryCheckRequest;
import com.app.pis.dto.response.InventoryCheckDetailResponse;
import com.app.pis.dto.response.InventoryCheckResponse;
import com.app.pis.entity.InventoryCheck;
import com.app.pis.entity.InventoryCheckDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryCheckMapper {

    @Mapping(target = "userId", source = "user.id")
    InventoryCheckResponse toResponse(InventoryCheck check);

    @Mapping(target = "inventoryId", source = "inventory.id")
    InventoryCheckDetailResponse toDetailResponse(InventoryCheckDetail detail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "details", ignore = true)
    InventoryCheck toEntity(InventoryCheckRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventory", ignore = true)
    @Mapping(target = "inventoryQuantity", ignore = true)
    @Mapping(target = "inventoryCheck", ignore = true)
    InventoryCheckDetail toDetailEntity(InventoryCheckDetailRequest request);
}
