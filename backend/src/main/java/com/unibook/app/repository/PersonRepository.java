package com.unibook.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.Person;

public interface PersonRepository  extends JpaRepository<Person, Long> {

    java.util.Optional<Person> findByName(String name);
    Boolean existsByEmail(String email);

}
