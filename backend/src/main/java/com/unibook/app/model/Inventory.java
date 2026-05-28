package com.unibook.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "inventory", uniqueConstraints = {
    @UniqueConstraint(name = "UniqueInventorySlot", columnNames = {"sector", "shelf", "row", "slot"})
})
public class Inventory extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String sector;
    
    @Column(nullable = false)
    private String shelf;
    
    @Column(nullable = false)
    private int row;

    @Column(nullable = false)
    private int slot;

    @JsonIgnore
    @OneToOne(mappedBy = "inventory")
    private Copy copy;
        
}
