package com.example.basicauthimplementation.v1.repository;

import com.example.basicauthimplementation.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Users, Long> {
    UserDetails findUsersByUsername(String username);
}
