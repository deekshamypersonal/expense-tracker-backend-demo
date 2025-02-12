package com.expensetracker.expensetracker.insights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class InsightScheduler {

    @Autowired
    private InsightService insightService;

    /**
     * Runs every Saturday at 9 AM.
     * Cron format: second minute hour day-of-month month day-of-week
     * "0 0 9 ? * SAT" => at 09:00:00 every Saturday
     */
    //@Scheduled(cron = "0 0 9 ? * SAT")
    public void runWeeklyInsightGeneration() {
        insightService.generateWeeklyInsightsForAllUsers();
    }
}
