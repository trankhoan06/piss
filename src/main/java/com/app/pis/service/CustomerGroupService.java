package com.app.pis.service;

import com.app.pis.dto.request.CustomerGroupRequest;
import com.app.pis.dto.response.CustomerGroupResponse;
import com.app.pis.entity.CustomerGroup;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.CustomerGroupMapper;
import com.app.pis.repository.CustomerGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerGroupService {

    @Autowired
    private CustomerGroupRepository customerGroupRepository;

    @Autowired
    private CustomerGroupMapper customerGroupMapper;

    @Transactional(readOnly = true)
    public List<CustomerGroupResponse> getAll() {
        return customerGroupRepository.findAll().stream()
                .map(customerGroupMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerGroupResponse create(CustomerGroupRequest request) {
        if (customerGroupRepository.existsByName(request.name())) {
            throw new BadRequestException("Tên nhóm khách hàng đã tồn tại");
        }
        CustomerGroup entity = customerGroupMapper.toEntity(request);
        return customerGroupMapper.toResponse(customerGroupRepository.save(entity));
    }

    @Transactional
    public CustomerGroupResponse update(Integer id, CustomerGroupRequest request) {
        CustomerGroup entity = customerGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy nhóm khách hàng"));

        if (!entity.getName().equals(request.name()) && customerGroupRepository.existsByName(request.name())) {
            throw new BadRequestException("Tên nhóm khách hàng đã tồn tại");
        }

        entity.setName(request.name());
        entity.setDescription(request.description());
        return customerGroupMapper.toResponse(customerGroupRepository.save(entity));
    }

    @Transactional
    public void delete(Integer id) {
        if (!customerGroupRepository.existsById(id)) {
            throw new BadRequestException("Không tìm thấy nhóm khách hàng");
        }
        customerGroupRepository.deleteById(id);
    }
}
