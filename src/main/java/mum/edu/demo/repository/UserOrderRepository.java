package mum.edu.demo.repository;

import mum.edu.demo.demain.User;
import mum.edu.demo.demain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

    @Query(value="SELECT o FROM UserOrder o left join o.product p left join p.owner ow WHERE ow.id =:id")
    public List<UserOrder> getOrdersBySeller(Long id);
}
