package com.expensetracker.expensetracker.insights;

import com.expensetracker.expensetracker.io.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "insights")
public class Insight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We'll store the insight text here. Use TEXT or LONGTEXT as needed.
    @Column(columnDefinition = "TEXT")
    private String insightText;

    private LocalDateTime generatedAt;

    // Associate with the user who owns this insight
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
