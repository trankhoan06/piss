package com.app.pis.mapper;

import com.app.pis.dto.response.MedicineResponse;
import com.app.pis.dto.response.MedicineUnitResponse;
import com.app.pis.entity.Medicine;
import com.app.pis.entity.MedicineUnit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicineMapper {
    MedicineResponse toResponse(Medicine medicine);
    MedicineUnitResponse toUnitResponse(MedicineUnit medicineUnit);
}
