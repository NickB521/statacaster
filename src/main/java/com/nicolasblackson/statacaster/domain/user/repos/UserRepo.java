package com.nicolasblackson.statacaster.domain.user.repos;

import com.nicolasblackson.statacaster.domain.user.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByFirstName(String firstName);
}
