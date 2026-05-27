package com.app.pis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RevenueChartData(
        LocalDate date,
        BigDecimal revenue
) {
}
