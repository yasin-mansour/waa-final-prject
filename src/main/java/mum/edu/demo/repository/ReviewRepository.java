package mum.edu.demo.repository;

import mum.edu.demo.demain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Integer> {

    @Query(value="SELECT r FROM  User u left join u.products p left  join p.orders o left join o.review r where r.active = true and u.id =:id")
    public List<Review> getSellerReviews(Long id);
}
