package mum.edu.demo.demain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Review {
    public static class ReviewBuilder implements Builder<Review> {
        private Review review;

        public ReviewBuilder() {
            this.review = new Review();
        }

        public Review.ReviewBuilder withId(int id){
            review.setId(id);
            return this;
        }

        public Review.ReviewBuilder withReview(String text){
            this.review.setText(text);
            return this;
        }


        @Override
        public Review build() {
            return review;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min=5, max = 255, message = "{Size.name.validation}")
    private String text;
    private boolean active;

    @OneToOne(mappedBy = "review", cascade = CascadeType.DETACH)
    private UserOrder order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String review) {
        this.text = review;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserOrder getOrder() {
        return order;
    }

    public void setOrder(UserOrder order) {
        this.order = order;
    }

    public static Review.ReviewBuilder create() {
        return new Review.ReviewBuilder();
    }
}
