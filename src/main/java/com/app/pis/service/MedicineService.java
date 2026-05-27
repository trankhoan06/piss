package com.app.pis.service;

import com.app.pis.config.ReflectionMapping;
import com.app.pis.dto.request.MedicineRequest;
import com.app.pis.entity.Medicine;
import com.app.pis.entity.MedicineUnit;
import com.app.pis.repository.CategoryRepository;
import com.app.pis.repository.MedicineRepository;
import com.app.pis.repository.MedicineUnitRepository;
import com.app.pis.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineUnitRepository medicineUnitRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UnitRepository unitRepository;

    @Transactional
    public Medicine createMedicine(MedicineRequest request) {
        Medicine medicine = Medicine.builder()
                .id(request.id())
                .name(request.name())
                .activeIngredient(request.activeIngredient())
                .manufacturerName(request.manufacturerName())
                .sellingPrice(request.sellingPrice())
                .description(request.description())
                .category(categoryRepository.getReferenceById(request.categoryId()))
                .baseUnit(unitRepository.getReferenceById(request.baseUnitId()))
                .build();
        List<MedicineUnit> units = request.medicineUnit()
                .stream()
                .map(c -> MedicineUnit.builder()
                        .conversionRate(c.conversionRate())
                        .note(c.note())
                        .unit(unitRepository.getReferenceById(c.unitId()))
                        .medicine(medicine)
                        .build())
                .toList();
        medicine.setMedicineUnits(units);
        return medicineRepository.save(medicine);
    }

    @Transactional
    public void deleteMedicine (String id) {
        medicineRepository.delete(medicineRepository.getReferenceById(id));
    }


    @Transactional
    public void updateMedicine (String id, MedicineRequest request) {
        Medicine entityUpdate = medicineRepository.findById(id).orElseThrow();



    }



}
