package com.app.pis.mapper;

import com.app.pis.dto.request.CashReceiptRequest;
import com.app.pis.dto.response.CashReceiptResponse;
import com.app.pis.entity.CashReceipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CashReceiptMapper {

    @Mapping(target = "userId", source = "user.id")
    CashReceiptResponse toResponse(CashReceipt receipt);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receiptDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    CashReceipt toEntity(CashReceiptRequest request);
}
