package mum.edu.demo.serviceImpl;

import mum.edu.demo.demain.Product;
import mum.edu.demo.repository.ProductRepository;
import mum.edu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> findNotSold() {
        return productRepository.findNotSold();
    }

    public void deleteById(int id){
        productRepository.deleteById(id);
    }
}
