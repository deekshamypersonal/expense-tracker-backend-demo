package com.expensetracker.expensetracker;

import com.expensetracker.expensetracker.io.entity.User;
import com.expensetracker.expensetracker.io.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableJpaRepositories(basePackages = "com.expensetracker.expensetracker.io.repository")
@SpringBootApplication
public class ExpensetrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensetrackerApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		return args -> {
			if (userRepository.findByEmail("userdemo@gmail.com") == null) {
				User demoUser = new User();
				demoUser.setIdentifier("demo");
				demoUser.setFirstName("Demo");
				demoUser.setLastName("User");
				demoUser.setEmail("userdemo@gmail.com");
				demoUser.setEncryptedPassword(encoder.encode("Demo2024!"));
				userRepository.save(demoUser);
			}
		};
	}
}

