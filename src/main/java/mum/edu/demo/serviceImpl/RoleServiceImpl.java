package mum.edu.demo.serviceImpl;

import mum.edu.demo.demain.Role;
import mum.edu.demo.repository.RoleRepository;
import mum.edu.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> findByRole(String role) {
        return roleRepository.findByRole(role);
    }
}
