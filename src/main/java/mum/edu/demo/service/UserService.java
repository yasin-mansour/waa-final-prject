package mum.edu.demo.service;

import mum.edu.demo.demain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User save(User user);
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(Long id);
    public List<User> getSellers();
}
