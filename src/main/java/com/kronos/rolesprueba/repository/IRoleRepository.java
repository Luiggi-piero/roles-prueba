package com.kronos.rolesprueba.repository;

import com.kronos.rolesprueba.model.Role;
import com.kronos.rolesprueba.model.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RolesEnum name);
}
