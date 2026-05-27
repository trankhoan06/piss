package com.app.pis.mapper;

import com.app.pis.dto.request.ImportReceiptDetailRequest;
import com.app.pis.dto.request.ImportReceiptRequest;
import com.app.pis.dto.response.ImportReceiptDetailResponse;
import com.app.pis.dto.response.ImportReceiptResponse;
import com.app.pis.entity.ImportReceipt;
import com.app.pis.entity.ImportReceiptDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImportReceiptMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "supplierId", source = "supplier.id")
    ImportReceiptResponse toResponse(ImportReceipt receipt);

    @Mapping(target = "medicineId", source = "medicine.id")
    ImportReceiptDetailResponse toDetailResponse(ImportReceiptDetail detail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "importReceiptDetails", ignore = true)
    ImportReceipt toEntity(ImportReceiptRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicine", ignore = true)
    @Mapping(target = "importReceipt", ignore = true)
    ImportReceiptDetail toDetailEntity(ImportReceiptDetailRequest request);
}
