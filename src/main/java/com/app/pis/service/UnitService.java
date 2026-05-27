package com.app.pis.service;

import com.app.pis.dto.request.UnitRequest;
import com.app.pis.dto.response.UnitResponse;
import com.app.pis.entity.Unit;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.UnitMapper;
import com.app.pis.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UnitMapper unitMapper;

    @Transactional
    public UnitResponse createUnit (UnitRequest request) {
        if (unitRepository.existsByName(request.name())) {
            throw new BadRequestException("Unit name already exists");
        }
        Unit unit = unitRepository.save(unitMapper.toEntity(request));
        return unitMapper.toResponse(unit);
    }
    @Transactional(readOnly = true)
    public List<UnitResponse> getAllUnits () {
        return unitRepository.getAll().map(unitMapper::toResponse).toList();
    }

    @Transactional
    public UnitResponse updateUnit(Integer id, UnitRequest request) {
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new BadRequestException("Unit not found"));
        if (request.name() != null) {
            unit.setName(request.name());
        }
        if (request.note() != null) {
            unit.setNote(request.note());
        }
        Unit unitUpdate = unitRepository.save(unit);
        return unitMapper.toResponse(unitUpdate);
    }

    @Transactional
    public void deleteUnit(Integer id) {
        Unit unit = unitRepository
                    .findById(id)
                    .orElseThrow(() -> new BadRequestException("Unit not found"));
        try {
            unitRepository.delete(unit);
        } catch (Exception e) {
            throw new BadRequestException("Cannot delete unit");
        }
    }
}
