//package com.expensetracker.expensetracker;
//
//import com.expensetracker.expensetracker.io.entity.User;
//import com.expensetracker.expensetracker.io.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//@EnableJpaRepositories(basePackages = "com.expensetracker.expensetracker.io.repository")
//@SpringBootApplication
//public class ExpensetrackerApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ExpensetrackerApplication.class, args);
//	}
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	CommandLineRunner initDatabase(UserRepository userRepository, BCryptPasswordEncoder encoder) {
//		return args -> {
//			if (userRepository.findByEmail("userdemo@gmail.com") == null) {
//				User demoUser = new User();
//				demoUser.setIdentifier("demo");
//				demoUser.setFirstName("Demo");
//				demoUser.setLastName("User");
//				demoUser.setEmail("userdemo@gmail.com");
//				demoUser.setEncryptedPassword(encoder.encode("Demo2024!"));
//				userRepository.save(demoUser);
//			}
//		};
//	}
//}
//

package com.expensetracker.expensetracker;

import com.expensetracker.expensetracker.insights.Insight;
import com.expensetracker.expensetracker.insights.InsightRepository;
import com.expensetracker.expensetracker.io.entity.Budget;
import com.expensetracker.expensetracker.io.entity.Expense;
import com.expensetracker.expensetracker.io.entity.User;
import com.expensetracker.expensetracker.io.repository.BudgetRepository;
import com.expensetracker.expensetracker.io.repository.ExpenseRepository;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@EnableJpaRepositories(basePackages = "com.expensetracker.expensetracker.io.repository","com.expensetracker.expensetracker.insights")
@EnableJpaRepositories(basePackages = {
        "com.expensetracker.expensetracker.io.repository",
        "com.expensetracker.expensetracker.insights"
})
@SpringBootApplication
@EnableScheduling
public class ExpensetrackerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExpensetrackerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * This CommandLineRunner runs at app startup. It creates a demo user if not present,
     * then inserts 6 default expenses and budgets if they don't exist yet.
     */
    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            ExpenseRepository expenseRepository,
            BudgetRepository budgetRepository,
            InsightRepository insightRepository,
            BCryptPasswordEncoder encoder
    ) {
        return args -> {

            // 1) Create or find the demo user
            User demoUser = userRepository.findByEmail("userdemo@gmail.com");
            if (demoUser == null) {
                demoUser = new User();
                demoUser.setIdentifier("demo");
                demoUser.setFirstName("Demo");
                demoUser.setLastName("User");
                demoUser.setEmail("userdemo@gmail.com");
                demoUser.setEncryptedPassword(encoder.encode("Demo2024!"));
                userRepository.save(demoUser);
            }

            // 2) If the demo user has NO expenses, create some default ones
            //    This prevents duplicates each time the dyno restarts
            if (demoUser.getExpenses() == null || demoUser.getExpenses().isEmpty()) {
                Expense e1 = new Expense();
                e1.setDescription("Groceries at Walmart");
                e1.setCategory("food_and_dining");
                e1.setAmount(75.50);
                e1.setDate(LocalDate.now());
                e1.setEmail(demoUser.getEmail());
                e1.setUser(demoUser);

                Expense e11 = new Expense();
                e11.setDescription("Diner");
                e11.setCategory("food_and_dining");
                e11.setAmount(30.25);
                e11.setDate(LocalDate.now());
                e11.setEmail(demoUser.getEmail());
                e11.setUser(demoUser);

                Expense e2 = new Expense();
                e2.setDescription("Flight");
                e2.setCategory("transportation");
                e2.setAmount(76.00);
                e2.setDate(LocalDate.now());
                e2.setEmail(demoUser.getEmail());
                e2.setUser(demoUser);

                Expense e3 = new Expense();
                e3.setDescription("Rent Payment");
                e3.setCategory("housing");
                e3.setAmount(400.00);
                e3.setDate(LocalDate.now());
                e3.setEmail(demoUser.getEmail());
                e3.setUser(demoUser);

                Expense e4 = new Expense();
                e4.setDescription("Movie Night Out");
                e4.setCategory("entertainment");
                e4.setAmount(80.75);
                e4.setDate(LocalDate.now());
                e4.setEmail(demoUser.getEmail());
                e4.setUser(demoUser);

                Expense e5 = new Expense();
                e5.setDescription("Trip to Vegas");
                e5.setCategory("travel");
                e5.setAmount(120.00);
                e5.setDate(LocalDate.now());
                e5.setEmail(demoUser.getEmail());
                e5.setUser(demoUser);

                Expense e6 = new Expense();
                e6.setDescription("Online Course");
                e6.setCategory("education");
                e6.setAmount(80.00);
                e6.setDate(LocalDate.now());
                e6.setEmail(demoUser.getEmail());
                e6.setUser(demoUser);

                expenseRepository.saveAll(List.of(e1, e11, e2, e3, e4, e5, e6));
            }

            // 3) If the demo user has NO budgets, create some default ones
            if (demoUser.getBudgets() == null || demoUser.getBudgets().isEmpty()) {
                Budget b1 = new Budget();
                b1.setCategory("food_and_dining");
                b1.setAmount(300.0);
                b1.setEmail(demoUser.getEmail());
                b1.setUser(demoUser);

                Budget b2 = new Budget();
                b2.setCategory("transportation");
                b2.setAmount(100.0);
                b2.setEmail(demoUser.getEmail());
                b2.setUser(demoUser);

                Budget b3 = new Budget();
                b3.setCategory("housing");
                b3.setAmount(500.0);
                b3.setEmail(demoUser.getEmail());
                b3.setUser(demoUser);

                Budget b4 = new Budget();
                b4.setCategory("entertainment");
                b4.setAmount(200.0);
                b4.setEmail(demoUser.getEmail());
                b4.setUser(demoUser);

                Budget b5 = new Budget();
                b5.setCategory("travel");
                b5.setAmount(250.0);
                b5.setEmail(demoUser.getEmail());
                b5.setUser(demoUser);

                Budget b6 = new Budget();
                b6.setCategory("education");
                b6.setAmount(150.0);
                b6.setEmail(demoUser.getEmail());
                b6.setUser(demoUser);

                budgetRepository.saveAll(List.of(b1, b2, b3, b4, b5, b6));
            }


            Optional<Insight> optionalExisting = insightRepository.findByUserEmail(demoUser.getEmail());

            Insight insight = new Insight();

            insight.setUser(demoUser);
            insight.setGeneratedAt(LocalDateTime.now());
            insight.setInsightText("**Spending Analysis for Demo**\\n\\nDemo is within budget for most categories this month. However, they are at risk of exceeding their budget in the following areas:\\n\\n- **Food and dining:** Demo has already spent $105.75 out of their $300 budget. They should consider reducing their spending in this category to avoid going over budget.\\n- **Entertainment:** Demo has spent $80.75 out of their $200 budget. They should monitor their entertainment expenses to ensure they stay within budget.\\n\\n**Cost-saving Suggestions:**\\n\\n- **Food and dining:** Demo could save money on food by cooking meals at home instead of eating out. They could also explore discounts and promotions at restaurants.\\n- **Entertainment:** Demo could save money on entertainment by attending free events or taking advantage of discounts on movies, concerts, and other activities.");


            // 5) Save
            insightRepository.save(insight);

        };
    }
}

