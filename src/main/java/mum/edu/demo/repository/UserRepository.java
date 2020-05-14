package mum.edu.demo.repository;

import mum.edu.demo.demain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);
    Optional<User> findById(Long id);

    @Query(value="SELECT u FROM User u left join u.roles r WHERE r.role = 'ROLE_SELLER'")
    public List<User> getSellers();
}
