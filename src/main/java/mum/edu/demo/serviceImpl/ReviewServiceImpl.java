package mum.edu.demo.serviceImpl;
import mum.edu.demo.demain.Review;
import mum.edu.demo.demain.User;
import mum.edu.demo.repository.ReviewRepository;
import mum.edu.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public Review save(Review review){
        return  this.reviewRepository.save(review);
    }

    public Optional<Review> findById(int id){
        return reviewRepository.findById(id);
    }

    public List<Review> getSellerReviews(Long id){
        return reviewRepository.getSellerReviews(id);
    }
}
