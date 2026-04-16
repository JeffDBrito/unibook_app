package com.unibook.app.model;

import java.time.LocalDate;

import com.unibook.app.enums.LoanStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "loans")
@Getter
@Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;
}
