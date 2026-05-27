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

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.app.pis.dto.wrap.PageResponse;
import com.app.pis.dto.response.MedicineResponse;
import com.app.pis.mapper.MedicineMapper;

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
    
    @Autowired
    private MedicineMapper medicineMapper;

    @Transactional(readOnly = true)
    public PageResponse<MedicineResponse> getAllMedicines(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Medicine> medicinePage;
        if (search != null && !search.isEmpty()) {
            medicinePage = medicineRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            medicinePage = medicineRepository.findAll(pageable);
        }

        List<MedicineResponse> content = medicinePage.getContent().stream()
                .map(medicineMapper::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                medicinePage.getNumber(),
                medicinePage.getSize(),
                medicinePage.getTotalElements(),
                medicinePage.getTotalPages(),
                medicinePage.isLast()
        );
    }

    @Transactional
    public MedicineResponse createMedicine(MedicineRequest request) {
        Medicine medicine = Medicine.builder()
                .id(request.id())
                .name(request.name())
                .activeIngredient(request.activeIngredient())
                .manufacturerName(request.manufacturerName())
                .sellingPrice(request.sellingPrice())
                .description(request.description())
                .category(categoryRepository.getReferenceById(request.categoryId()))
                .baseUnit(unitRepository.getReferenceById(request.baseUnitId()))
                .status("ACTIVE")
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
        return medicineMapper.toResponse(medicineRepository.save(medicine));
    }

    @Transactional
    public void deleteMedicine (String id) {
        medicineRepository.delete(medicineRepository.getReferenceById(id));
    }


    @Transactional
    public MedicineResponse updateMedicine(String id, MedicineRequest request) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Medicine not found"));
        
        medicine.setName(request.name());
        medicine.setActiveIngredient(request.activeIngredient());
        medicine.setManufacturerName(request.manufacturerName());
        medicine.setSellingPrice(request.sellingPrice());
        medicine.setDescription(request.description());
        medicine.setCategory(categoryRepository.getReferenceById(request.categoryId()));
        medicine.setBaseUnit(unitRepository.getReferenceById(request.baseUnitId()));

        // Update units by removing old and adding new
        medicineUnitRepository.deleteAll(medicine.getMedicineUnits());
        
        List<MedicineUnit> units = request.medicineUnit().stream()
                .map(c -> MedicineUnit.builder()
                        .conversionRate(c.conversionRate())
                        .note(c.note())
                        .unit(unitRepository.getReferenceById(c.unitId()))
                        .medicine(medicine)
                        .build())
                .toList();
        medicine.setMedicineUnits(units);
        
        return medicineMapper.toResponse(medicineRepository.save(medicine));
    }

    @Transactional
    public MedicineResponse toggleStatus(String id) {
        Medicine medicine = medicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Medicine not found"));
        if ("ACTIVE".equals(medicine.getStatus())) {
            medicine.setStatus("INACTIVE");
        } else {
            medicine.setStatus("ACTIVE");
        }
        return medicineMapper.toResponse(medicineRepository.save(medicine));
    }



}
