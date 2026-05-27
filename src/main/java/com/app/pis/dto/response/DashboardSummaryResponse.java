package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummaryResponse(
        BigDecimal todayRevenue,
        BigDecimal monthRevenue,
        long todayInvoiceCount,
        long monthInvoiceCount
) {
}
