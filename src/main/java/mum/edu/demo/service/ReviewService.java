package mum.edu.demo.service;

import mum.edu.demo.demain.Review;
import mum.edu.demo.demain.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    public Review save(Review review);
    public Optional<Review> findById(int id);
    public List<Review> getSellerReviews(Long id);
}
