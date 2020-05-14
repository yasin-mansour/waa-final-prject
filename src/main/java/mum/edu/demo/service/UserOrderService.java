package mum.edu.demo.service;

import mum.edu.demo.demain.UserOrder;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {
    public List<UserOrder> findAll();
    public Optional<UserOrder> findById(int id);
    public UserOrder save(UserOrder order);
    public void delete(UserOrder order);
    public List<UserOrder> getOrdersBySeller(Long id);
}
