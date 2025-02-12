//package com.expensetracker.expensetracker.insights;
//
//import com.expensetracker.expensetracker.io.entity.Budget;
//import com.expensetracker.expensetracker.io.entity.Expense;
//import com.expensetracker.expensetracker.io.entity.User;
//import com.expensetracker.expensetracker.io.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//public class InsightController {
//
//    @Autowired
//    private InsightService insightService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/test")
//    public String test() {
//        // 1) Get logged-in user email from security context
//
//        return "test1";
//    }
//
//    @GetMapping("/api/insights")
//    public InsightResponse getInsightsForCurrentUser() {
//        // 1) Get logged-in user email from security context
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//        // fetch the single insight
//        Insight insight = insightService.getInsightForUser(email);
//        return convertToDto(insight);
//    }
//
//
//    private InsightResponse convertToDto(Insight insight) {
//        if (insight == null) return null; // handle no insight found
//        InsightResponse dto = new InsightResponse();
//        dto.setId(insight.getId());
//        dto.setInsightText(insight.getInsightText());
//        dto.setGeneratedAt(insight.getGeneratedAt());
//        return dto;
//    }
//}
