package mum.edu.demo.demain;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
public class UserOrder implements Cloneable {
    public static class UserOrderBuilder implements Builder<UserOrder> {
        private UserOrder userOrder;

        public UserOrderBuilder() {
            this.userOrder = new UserOrder();
        }

        public UserOrder.UserOrderBuilder withId(int id) {
            userOrder.setId(id);
            return this;
        }

        public UserOrder.UserOrderBuilder withStatus(String status) {
            userOrder.setStatus(status);
            return this;
        }

        public UserOrder.UserOrderBuilder withProduct(Product product) {
            userOrder.setProduct(product);
            return this;
        }

        public UserOrder.UserOrderBuilder withUser(User user) {
            userOrder.setUser(user);
            return this;
        }

        public UserOrder.UserOrderBuilder withAddress(Address address) {
            userOrder.setAddress(address);
            return this;
        }

        public UserOrder.UserOrderBuilder withPayment(Payment payment) {
            userOrder.setPayment(payment);
            return this;
        }

        @Override
        public UserOrder build() {
            return userOrder;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;

    @OneToOne
    private Product product;

    @OneToOne
    private Review review;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static UserOrder.UserOrderBuilder create() {
        return new UserOrder.UserOrderBuilder();
    }
}
