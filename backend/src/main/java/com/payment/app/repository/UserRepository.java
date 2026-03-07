package com.payment.app.repository;

import com.payment.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // findAll()
    // findById()
    // save()
    // delete()

}