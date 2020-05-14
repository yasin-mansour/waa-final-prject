package mum.edu.demo.repository;

import mum.edu.demo.demain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Optional<Role> findByRole(String role);
}
