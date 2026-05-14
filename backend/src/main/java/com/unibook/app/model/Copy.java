package com.unibook.app.model;

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
public class Copy extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private CopyStatus status;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "inventory_id", unique = true)
    private Inventory inventory;
}
