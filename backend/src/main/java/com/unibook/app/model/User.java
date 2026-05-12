package com.unibook.app.model;


import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE id = ?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}