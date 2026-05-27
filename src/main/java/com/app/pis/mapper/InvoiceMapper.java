package com.app.pis.mapper;

import com.app.pis.dto.request.InvoiceDetailRequest;
import com.app.pis.dto.request.InvoiceRequest;
import com.app.pis.dto.response.InvoiceDetailResponse;
import com.app.pis.dto.response.InvoiceResponse;
import com.app.pis.entity.Invoice;
import com.app.pis.entity.InvoiceDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "userId", source = "user.id")
    InvoiceResponse toResponse(Invoice invoice);

    @Mapping(target = "medicineId", source = "medicine.id")
    InvoiceDetailResponse toDetailResponse(InvoiceDetail detail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "invoiceDetails", ignore = true)
    Invoice toEntity(InvoiceRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "medicine", ignore = true)
    InvoiceDetail toDetailEntity(InvoiceDetailRequest request);
}
