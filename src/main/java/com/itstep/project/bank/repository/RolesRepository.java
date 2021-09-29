package com.itstep.project.bank.repository;

import com.itstep.project.bank.model.Clients;
import com.itstep.project.bank.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Set<Roles> findRolesByName(String name);
    Roles findByName(String name);
}
