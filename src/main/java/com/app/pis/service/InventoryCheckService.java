package com.app.pis.service;

import com.app.pis.dto.request.InventoryCheckDetailRequest;
import com.app.pis.dto.request.InventoryCheckRequest;
import com.app.pis.dto.response.InventoryCheckResponse;
import com.app.pis.entity.Inventory;
import com.app.pis.entity.InventoryCheck;
import com.app.pis.entity.InventoryCheckDetail;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.InventoryCheckMapper;
import com.app.pis.repository.InventoryCheckDetailRepository;
import com.app.pis.repository.InventoryCheckRepository;
import com.app.pis.repository.InventoryRepository;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryCheckService {

    @Autowired
    private InventoryCheckRepository inventoryCheckRepository;

    @Autowired
    private InventoryCheckDetailRepository inventoryCheckDetailRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryCheckMapper inventoryCheckMapper;

    @Transactional(readOnly = true)
    public List<InventoryCheckResponse> getAllInventoryChecks() {
        return inventoryCheckRepository.findAll().stream()
                .map(inventoryCheckMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryCheckResponse createInventoryCheck(InventoryCheckRequest request) {
        InventoryCheck check = inventoryCheckMapper.toEntity(request);
        check.setDate(LocalDate.now());
        check.setStatus("TEMPORARY");

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        check.setUser(user);

        InventoryCheck savedCheck = inventoryCheckRepository.save(check);
        List<InventoryCheckDetail> details = new ArrayList<>();

        for (InventoryCheckDetailRequest detailReq : request.details()) {
            InventoryCheckDetail detail = inventoryCheckMapper.toDetailEntity(detailReq);
            Inventory inventory = inventoryRepository.findById(detailReq.inventoryId())
                    .orElseThrow(() -> new BadRequestException("Inventory not found with ID: " + detailReq.inventoryId()));

            detail.setInventoryQuantity(inventory.getStockQuantity());
            detail.setActualQuantity(detailReq.actualQuantity());

            detail.setInventory(inventory);
            detail.setInventoryCheck(savedCheck);
            details.add(inventoryCheckDetailRepository.save(detail));
        }

        savedCheck.setDetails(details);
        return inventoryCheckMapper.toResponse(savedCheck);
    }

    @Transactional
    public InventoryCheckResponse acceptInventoryCheck(Integer id) {
        InventoryCheck check = inventoryCheckRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Inventory Check not found"));

        if (!"TEMPORARY".equals(check.getStatus())) {
            throw new BadRequestException("Only TEMPORARY checks can be accepted");
        }

        for (InventoryCheckDetail detail : check.getDetails()) {
            Inventory inventory = detail.getInventory();
            inventory.setStockQuantity(detail.getActualQuantity());
            inventoryRepository.save(inventory);
        }

        check.setStatus("ACCEPTED");
        return inventoryCheckMapper.toResponse(inventoryCheckRepository.save(check));
    }
}
