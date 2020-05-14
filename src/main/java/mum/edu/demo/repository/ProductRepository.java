package mum.edu.demo.repository;

import mum.edu.demo.demain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value="SELECT p FROM  Product p WHERE p.isSold = false ")
    public List<Product> findNotSold();
}
