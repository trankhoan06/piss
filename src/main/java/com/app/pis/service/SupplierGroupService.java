package com.app.pis.service;

import com.app.pis.dto.request.SupplierGroupRequest;
import com.app.pis.dto.response.SupplierGroupResponse;
import com.app.pis.entity.SupplierGroup;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.SupplierGroupMapper;
import com.app.pis.repository.SupplierGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierGroupService {

    @Autowired
    private SupplierGroupRepository supplierGroupRepository;

    @Autowired
    private SupplierGroupMapper supplierGroupMapper;

    @Transactional(readOnly = true)
    public List<SupplierGroupResponse> getAll() {
        return supplierGroupRepository.findAll().stream()
                .map(supplierGroupMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SupplierGroupResponse create(SupplierGroupRequest request) {
        if (supplierGroupRepository.existsByName(request.name())) {
            throw new BadRequestException("Tên nhóm nhà cung cấp đã tồn tại");
        }
        SupplierGroup entity = supplierGroupMapper.toEntity(request);
        return supplierGroupMapper.toResponse(supplierGroupRepository.save(entity));
    }

    @Transactional
    public SupplierGroupResponse update(Integer id, SupplierGroupRequest request) {
        SupplierGroup entity = supplierGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy nhóm nhà cung cấp"));

        if (!entity.getName().equals(request.name()) && supplierGroupRepository.existsByName(request.name())) {
            throw new BadRequestException("Tên nhóm nhà cung cấp đã tồn tại");
        }

        entity.setName(request.name());
        entity.setDescription(request.description());
        return supplierGroupMapper.toResponse(supplierGroupRepository.save(entity));
    }

    @Transactional
    public void delete(Integer id) {
        if (!supplierGroupRepository.existsById(id)) {
            throw new BadRequestException("Không tìm thấy nhóm nhà cung cấp");
        }
        supplierGroupRepository.deleteById(id);
    }
}
