package com.unibook.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unibook.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // findAll()
    // findById()
    // save()
    // delete()

}