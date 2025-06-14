package com.expensetracker.expensetracker.insights;

import com.expensetracker.expensetracker.io.entity.Budget;
import com.expensetracker.expensetracker.io.entity.Expense;
import com.expensetracker.expensetracker.io.entity.User;
import com.expensetracker.expensetracker.io.repository.BudgetRepository;
import com.expensetracker.expensetracker.io.repository.ExpenseRepository;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsightService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private InsightRepository insightRepository;

    @Autowired
    private LlmClient llmClient;

    /**
     * Generate weekly insights for ALL users in the system.
     */
    public void generateWeeklyInsightsForAllUsers() {
        List<User> allUsers = (List<User>) userRepository.findAll();

        for (User user : allUsers) {
            try {
                generateInsightForUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateInsightForUser(User user) {
        // 1) Fetch budgets for user
        List<Budget> budgets = budgetRepository.findByEmail(user.getEmail());
        // 2) Fetch expenses for user
        List<Expense> expenses = expenseRepository.findByEmailForCurrentMonthAndYear(user.getEmail());

        // 3) Build a prompt with the relevant data
        String prompt = buildPrompt(user, budgets, expenses);


        String aiResponse = llmClient.generateTextFromPrompt(prompt);


        Optional<Insight> optionalExisting = insightRepository.findByUserEmail(user.getEmail());

        Insight insight;
        if (optionalExisting.isPresent()) {
            // 3) Overwrite existing record
            insight = optionalExisting.get();
            insight.setGeneratedAt(LocalDateTime.now());
            insight.setInsightText(aiResponse);
        } else {
            // 4) Otherwise, create a new record
            insight = new Insight();
            insight.setUser(user);
            insight.setGeneratedAt(LocalDateTime.now());
            insight.setInsightText(aiResponse);
        }

        // 5) Save
        insightRepository.save(insight);
    }

    /**
     * Builds a prompt from budgets and expenses.
     */
    private String buildPrompt(User user, List<Budget> budgets, List<Expense> expenses) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are a helpful financial assistant. Provide insights on ")
                .append(user.getFirstName())
                .append("'s spending so far this month.\n\n");

        // Summaries by category
        sb.append("Budgets:\n");
        for (Budget budget : budgets) {
            sb.append("- ").append(budget.getCategory())
                    .append(": $").append(budget.getAmount()).append("\n");
        }

        // Group expenses by category
        Map<String, Double> expenseByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));

        sb.append("\nExpenses this month:\n");
        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            sb.append("- ").append(entry.getKey())
                    .append(": $").append(String.format("%.2f", entry.getValue()))
                    .append("\n");
        }


        sb.append("\nPlease provide a concise but insightful analysis. "
                + "Highlight any categories where the user might exceed their budget soon, "
                + "and suggest cost-saving measures.\n");

        return sb.toString();
    }

    public Insight getInsightForUser(String email) {
        return insightRepository.findByUserEmail(email).orElse(null);
    }
}
