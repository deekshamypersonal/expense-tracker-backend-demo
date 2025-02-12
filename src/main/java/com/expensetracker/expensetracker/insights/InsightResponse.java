package com.expensetracker.expensetracker.insights;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InsightResponse {
    private Long id;
    private String insightText;
    private LocalDateTime generatedAt;

}
