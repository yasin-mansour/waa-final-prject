package mum.edu.demo.demain;

import mum.edu.demo.validation.UserEmail;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {
    public static class UserBuilder implements Builder<User> {
        private User user;

        public UserBuilder() {
            this.user = new User();
        }

        public User.UserBuilder withId(Long id) {
            user.setId(id);
            return this;
        }

        public User.UserBuilder withFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public User.UserBuilder withLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public User.UserBuilder withEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public User.UserBuilder withPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public User.UserBuilder withRoles(Set<Role> roles) {
            user.setRoles(roles);
            return this;
        }

        public User.UserBuilder withActive(int active) {
            user.setActive(active);
            return this;
        }

        public User.UserBuilder withProducts(List<Product> products) {
            user.setProducts(products);
            return this;
        }

        public User.UserBuilder withOrders(List<UserOrder> orders) {
            user.setOrders(orders);
            return this;
        }

        public User.UserBuilder withMoney(int money) {
            user.setMoney(money);
            return this;
        }


        @Override
        public User build() {
            return user;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 50, message= "{Size.name.validation}")
    private String firstName;

    @NotEmpty
    @Size(min = 4, max = 50, message= "{Size.name.validation}")
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = "username")
    @UserEmail
    private String email;

    @Size(min = 4, message= "{Size.password.validation}")
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    private int money;

    private int points;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<UserOrder> orders;

    @OneToMany(mappedBy = "owner")
    private List<Product> products;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "cart", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> cart;

    @ManyToMany
    private List<User> follows;

    @ManyToMany(mappedBy = "follows")
    private List<User> follower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UserOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<UserOrder> orders) {
        this.orders = orders;
    }

    public void addOrder(UserOrder order) {
        this.orders.add(order);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public void addToCart(Product product) {
        this.cart.add(product);
    }

    public List<User> getFollows() {
        return follows;
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }

    public static User.UserBuilder create() {
        return new User.UserBuilder();
    }

    public boolean canAddProduct() {
        return this.getRoles().stream().filter(role -> role.getRole() == "ROLE_PRODUCT").count() > 0;
    }

    public List<User> getFollower() {
        return follower;
    }

    public void setFollower(List<User> follower) {
        this.follower = follower;
    }

    public void addFollower(User user) {
        this.follower.add(user);
    }

    public void follow(User user) {
        this.follows.add(user);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getTotalMoney() {
        return this.getMoney() + (this.getPoints() / 10);
    }

    public int getCartTotalPrice() {
        int totalPrice = this.getCart().stream().mapToInt(Product::getPrice).sum();
        return totalPrice;
    }

    public void withdrawMoney(int money) {
        if (money > this.getMoney()) {
            money -= this.getMoney();
            this.setMoney(0);
            this.setPoints(this.getPoints() - (money * 10));
        } else {
            this.setMoney(this.getMoney() - money);
        }
    }

    public boolean isFollowedBy(String email) {
        return this.getFollower().stream().filter(user -> user.getEmail().equals(email)).count() > 0;
    }
}

