package com.kronos.rolesprueba.repository;

import com.kronos.rolesprueba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    boolean existsByUsername(String username);
}
