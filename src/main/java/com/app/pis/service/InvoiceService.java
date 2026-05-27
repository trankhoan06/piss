package com.app.pis.service;

import com.app.pis.dto.request.InvoiceDetailRequest;
import com.app.pis.dto.request.InvoiceRequest;
import com.app.pis.dto.response.InvoiceResponse;
import com.app.pis.entity.Customer;
import com.app.pis.entity.Invoice;
import com.app.pis.entity.InvoiceDetail;
import com.app.pis.entity.Medicine;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.InvoiceMapper;
import com.app.pis.repository.CustomerRepository;
import com.app.pis.repository.InvoiceDetailRepository;
import com.app.pis.repository.InvoiceRepository;
import com.app.pis.repository.MedicineRepository;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = invoiceMapper.toEntity(request);
        invoice.setSaleDate(LocalDateTime.now());
        invoice.setStatus("COMPLETED");

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        invoice.setUser(user);

        if (request.customerId() != null) {
            Customer customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new BadRequestException("Customer not found"));
            invoice.setCustomer(customer);
        }

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceDetail> details = new ArrayList<>();
        for (InvoiceDetailRequest detailReq : request.invoiceDetails()) {
            InvoiceDetail detail = invoiceMapper.toDetailEntity(detailReq);
            Medicine medicine = medicineRepository.findById(detailReq.medicineId())
                    .orElseThrow(() -> new BadRequestException("Medicine not found: " + detailReq.medicineId()));
            
            detail.setMedicine(medicine);
            detail.setInvoice(savedInvoice);
            details.add(invoiceDetailRepository.save(detail));
        }

        savedInvoice.setInvoiceDetails(details);
        return invoiceMapper.toResponse(savedInvoice);
    }

    @Transactional
    public InvoiceResponse cancelInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invoice not found"));

        if ("CANCELLED".equals(invoice.getStatus())) {
            throw new BadRequestException("Invoice is already cancelled");
        }

        invoice.setStatus("CANCELLED");
        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }
}
