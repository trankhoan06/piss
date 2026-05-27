package com.app.pis.mapper;

import com.app.pis.dto.request.ExportReceiptDetailRequest;
import com.app.pis.dto.request.ExportReceiptRequest;
import com.app.pis.dto.response.ExportReceiptDetailResponse;
import com.app.pis.dto.response.ExportReceiptResponse;
import com.app.pis.entity.ExportReceipt;
import com.app.pis.entity.ExportReceiptDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExportReceiptMapper {

    @Mapping(target = "userId", source = "user.id")
    ExportReceiptResponse toResponse(ExportReceipt receipt);

    @Mapping(target = "inventoryId", source = "inventory.id")
    ExportReceiptDetailResponse toDetailResponse(ExportReceiptDetail detail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exportReceiptDetails", ignore = true)
    ExportReceipt toEntity(ExportReceiptRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventory", ignore = true)
    @Mapping(target = "exportReceipt", ignore = true)
    ExportReceiptDetail toDetailEntity(ExportReceiptDetailRequest request);
}
