package com.nicolasblackson.statacaster.domain.user.repos;

import com.nicolasblackson.statacaster.domain.user.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByFirstName(String firstName);
}
