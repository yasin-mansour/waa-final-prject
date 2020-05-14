package mum.edu.demo.serviceImpl;

import mum.edu.demo.demain.UserOrder;
import mum.edu.demo.repository.UserOrderRepository;
import mum.edu.demo.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    UserOrderRepository userOrderRepository;

    public List<UserOrder> findAll() {
        return userOrderRepository.findAll();
    }

    public Optional<UserOrder> findById(int id){
        return userOrderRepository.findById(id);
    }

    public UserOrder save(UserOrder order) {
        return this.userOrderRepository.save(order);
    }

    public void delete(UserOrder order) {
        this.userOrderRepository.delete(order);
    }

    public List<UserOrder> getOrdersBySeller(Long id) {
        return userOrderRepository.getOrdersBySeller(id);
    }
}
