package mum.edu.demo.service;

import mum.edu.demo.demain.Role;

import java.util.Optional;

public interface RoleService {
    public Optional<Role> findByRole(String role);
}
