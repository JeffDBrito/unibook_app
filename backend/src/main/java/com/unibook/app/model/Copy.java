package com.unibook.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unibook.app.enums.CopyStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "copies")
@Getter
@Setter
@NoArgsConstructor
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private boolean available = true;

    private CopyStatus status;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
}
