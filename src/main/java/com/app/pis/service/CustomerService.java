package com.app.pis.service;

import com.app.pis.dto.request.CustomerRequest;
import com.app.pis.dto.response.CustomerResponse;
import com.app.pis.entity.Customer;
import com.app.pis.entity.CustomerGroup;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.CustomerMapper;
import com.app.pis.repository.CustomerGroupRepository;
import com.app.pis.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerGroupRepository customerGroupRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        
        if (request.customerGroupId() != null) {
            CustomerGroup group = customerGroupRepository.findById(request.customerGroupId())
                    .orElseThrow(() -> new BadRequestException("CustomerGroup not found"));
            customer.setCustomerGroup(group);
        }
        
        if (customer.getStatus() == null) {
            customer.setStatus("ACTIVE");
        }
        
        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }

    @Transactional
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        try {
            Class<?> clazz = request.getClass();
            Class<?> entityClass = customer.getClass();
            Field[] fields = clazz.getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(request);
                
                if (value != null && !field.getName().equals("customerGroupId")) {
                    Field entityField = entityClass.getDeclaredField(field.getName());
                    entityField.setAccessible(true);
                    entityField.set(customer, value);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException("Error updating fields");
        }

        if (request.customerGroupId() != null) {
            CustomerGroup group = customerGroupRepository.findById(request.customerGroupId())
                    .orElseThrow(() -> new BadRequestException("CustomerGroup not found"));
            customer.setCustomerGroup(group);
        }

        Customer updated = customerRepository.save(customer);
        return customerMapper.toResponse(updated);
    }

    @Transactional
    public void toggleStatus(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Customer not found"));
        
        if ("ACTIVE".equalsIgnoreCase(customer.getStatus())) {
            customer.setStatus("INACTIVE");
        } else {
            customer.setStatus("ACTIVE");
        }
        
        customerRepository.save(customer);
    }
}
