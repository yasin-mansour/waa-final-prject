package mum.edu.demo.demain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Product {


    public static class ProductBuilder implements Builder<Product> {
        private Product product;

        public ProductBuilder() {
            this.product = new Product();
        }

        public Product.ProductBuilder withId(int id) {
            product.setId(id);
            return this;
        }

        public Product.ProductBuilder withName(String name) {
            product.setName(name);
            return this;
        }

        public Product.ProductBuilder withDescription(String description) {
            product.setDescription(description);
            return this;
        }


        public Product.ProductBuilder withPrice(int Price) {
            product.setPrice(Price);
            return this;
        }

        public Product.ProductBuilder withOwner(User owner) {
            product.setOwner(owner);
            return this;
        }

        @Override
        public Product build() {
            return product;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @Size(min = 4, max = 50, message = "{Size.name.validation}")
    private String name;
    @NotEmpty
    @Size(min = 4, max = 255, message = "{Size.name.validation}")
    private String description;
    @Min(value = 1)
    private int price;

    @ManyToOne
    private User owner;

    @ManyToMany(mappedBy = "cart")
    private List<User> carts;

    @OneToMany(mappedBy = "product")
    private List<UserOrder> orders;

    private boolean isSold;

    @Transient
    private MultipartFile image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getCarts() {
        return carts;
    }
    public void addToCart(User user){
        this.carts.add(user);
    }

    public void setCarts(List<User> carts) {
        this.carts = carts;
    }

    public static Product.ProductBuilder create() {
        return new Product.ProductBuilder();
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public boolean isSold() {
        return this.isSold;
    }

    public List<UserOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<UserOrder> orders) {
        this.orders = orders;
    }

    public void addOrder(UserOrder order){
        this.orders.add(order);
    }

    public boolean inCart(String email){
            return this.getCarts().stream().filter(user -> user.getEmail().equals(email)).count() > 0;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
