package mum.edu.demo.serviceImpl;

import mum.edu.demo.demain.User;
import mum.edu.demo.repository.UserRepository;
import mum.edu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
    public Optional<User> findByEmail(String email){return userRepository.findByEmail(email);}
    public Optional<User> findById(Long id){return userRepository.findById(id);}
    public List<User> getSellers() { return userRepository.getSellers();}
}
