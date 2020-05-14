package mum.edu.demo.service;

import mum.edu.demo.demain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product save(Product product);
    public List<Product> findAll();
    public Optional<Product> findById(int id);
    public List<Product> findNotSold();
    public void deleteById(int id);
}
