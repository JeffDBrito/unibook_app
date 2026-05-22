package com.unibook.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.unibook.app.enums.FineStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "fines")
public class Fine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal amount;
    
    private String reason;

    private LocalDate issuedDate;

    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    private FineStatus status;
    
    @OneToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
}
