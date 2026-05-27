package com.app.pis.service;

import com.app.pis.dto.request.CashReceiptRequest;
import com.app.pis.dto.response.CashReceiptResponse;
import com.app.pis.entity.CashReceipt;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.CashReceiptMapper;
import com.app.pis.repository.CashReceiptRepository;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashReceiptService {

    @Autowired
    private CashReceiptRepository cashReceiptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CashReceiptMapper cashReceiptMapper;

    @Transactional(readOnly = true)
    public List<CashReceiptResponse> getAll() {
        return cashReceiptRepository.findAll().stream()
                .map(cashReceiptMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CashReceiptResponse create(CashReceiptRequest request) {
        if (!"INCOME".equals(request.type()) && !"EXPENSE".equals(request.type())) {
            throw new BadRequestException("Loại phiếu chỉ được là INCOME hoặc EXPENSE");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("Không tìm thấy User"));

        CashReceipt entity = cashReceiptMapper.toEntity(request);
        entity.setReceiptDate(LocalDateTime.now());
        entity.setUser(user);

        return cashReceiptMapper.toResponse(cashReceiptRepository.save(entity));
    }

    @Transactional
    public void delete(Integer id) {
        if (!cashReceiptRepository.existsById(id)) {
            throw new BadRequestException("Không tìm thấy phiếu thu chi");
        }
        cashReceiptRepository.deleteById(id);
    }
}
