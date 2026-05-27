package com.app.pis.service;

import com.app.pis.dto.response.DashboardSummaryResponse;
import com.app.pis.dto.response.RevenueChartData;
import com.app.pis.dto.response.TopMedicineData;
import com.app.pis.entity.Inventory;
import com.app.pis.repository.InventoryRepository;
import com.app.pis.repository.InvoiceDetailRepository;
import com.app.pis.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).with(LocalTime.MIN);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth()).with(LocalTime.MAX);

        BigDecimal todayRev = invoiceRepository.sumRevenueByDateRange(startOfDay, endOfDay);
        if (todayRev == null) todayRev = BigDecimal.ZERO;

        BigDecimal monthRev = invoiceRepository.sumRevenueByDateRange(startOfMonth, endOfMonth);
        if (monthRev == null) monthRev = BigDecimal.ZERO;

        long todayCount = invoiceRepository.countByDateRange(startOfDay, endOfDay);
        long monthCount = invoiceRepository.countByDateRange(startOfMonth, endOfMonth);

        return new DashboardSummaryResponse(todayRev, monthRev, todayCount, monthCount);
    }

    @Transactional(readOnly = true)
    public List<RevenueChartData> getRevenueChart() {
        // Mock data for MVP: In a real scenario, group by date from DB
        List<RevenueChartData> chart = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime start = date.atTime(LocalTime.MIN);
            LocalDateTime end = date.atTime(LocalTime.MAX);
            BigDecimal rev = invoiceRepository.sumRevenueByDateRange(start, end);
            chart.add(new RevenueChartData(date, rev != null ? rev : BigDecimal.ZERO));
        }
        return chart;
    }

    @Transactional(readOnly = true)
    public List<TopMedicineData> getTopMedicines() {
        List<Object[]> results = invoiceDetailRepository.findTopSellingMedicines(PageRequest.of(0, 10));
        return results.stream().map(obj -> new TopMedicineData(
                (String) obj[0],
                (String) obj[1],
                ((Number) obj[2]).longValue()
        )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAlerts() {
        // Find medicines with stock < 10 or expired
        List<Inventory> allStock = inventoryRepository.findAll();
        long outOfStockCount = allStock.stream().filter(inv -> inv.getStockQuantity() <= 5).count();
        long expiredCount = allStock.stream().filter(inv -> inv.getExpirationDate() != null && inv.getExpirationDate().isBefore(LocalDate.now())).count();

        Map<String, Object> alerts = new HashMap<>();
        alerts.put("outOfStockCount", outOfStockCount);
        alerts.put("expiredCount", expiredCount);
        return alerts;
    }
}
