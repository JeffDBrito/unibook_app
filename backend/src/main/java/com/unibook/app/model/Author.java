package com.unibook.app.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "authors")
@SQLDelete(sql = "UPDATE authors SET deleted_at = now() WHERE id = ?")
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private String biography;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
}
