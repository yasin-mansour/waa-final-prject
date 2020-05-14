package mum.edu.demo.seeds;

import mum.edu.demo.demain.*;
import mum.edu.demo.repository.ProductRepository;
import mum.edu.demo.repository.RoleRepository;
import mum.edu.demo.repository.UserOrderRepository;
import mum.edu.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class ApplicationSeeds {

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private UserOrderRepository userOrderRepository;

    public ApplicationSeeds(
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            UserOrderRepository userOrderRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userOrderRepository = userOrderRepository;
    }

    public void init(){

        Role  productRole = Role.create().withRole("ROLE_PRODUCT").build();
        Role  adminRole = Role.create().withRole("ROLE_ADMIN").build();
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

        User user = User.create().withFirstName("admin")
                .withLastName("admin")
                .withEmail("admin@waa.com")
                .withPassword(this.passwordEncoder.encode("admin"))
                .withRoles(roles)
                .withActive(1)
                .build();

        this.userRepository.save(user);

        Role  sellerRole = Role.create().withRole("ROLE_SELLER").build();
        Set<Role> sellerRoles = new HashSet<>();
        sellerRoles.add(sellerRole);
        sellerRoles.add(productRole);

        User seller = User.create().withFirstName("seller")
                .withLastName("seller")
                .withEmail("seller@waa.com")
                .withPassword(this.passwordEncoder.encode("seller"))
                .withRoles(sellerRoles)
                .withActive(1)
                .build();
        this.userRepository.save(seller);

        Product product2 = Product.create().withName("Product 2")
                .withDescription("Product 2 description")
                .withPrice(20)
                .withOwner(seller)
                .build();
        this.productRepository.save(product2);

        Product product3 = Product.create().withName("Product 3")
                .withDescription("Product 3 description")
                .withPrice(30)
                .withOwner(seller)
                .build();
        this.productRepository.save(product3);

        Product product = Product.create().withName("Product 1")
                .withDescription("Product 1 description")
                .withPrice(10)
                .withOwner(seller)
                .build();

        product = this.productRepository.save(product);

        Role  buyerRole = Role.create().withRole("ROLE_BUYER").build();
        Set<Role> BuyerRoles = new HashSet<>();
        BuyerRoles.add(buyerRole);

        UserOrder order = UserOrder.create()
                .withProduct(product)
                .withAddress(
                        Address.create()
                                .withStreet("1000 4th")
                                .withState("Iowa")
                                .withZipCode("52557")
                                .build())
                .withPayment(
                        Payment.create()
                                .withNumber("1234567891234567")
                                .withCode("1234")
                                .withHolderName("Yasin Mansour")
                                .build())
                .build();

        User buyer = User.create().withFirstName("buyer")
                .withLastName("buyer")
                .withEmail("buyer@waa.com")
                .withPassword(this.passwordEncoder.encode("buyer"))
                .withRoles(BuyerRoles)
                .withActive(1)
                .withMoney(1000)
                .withOrders(new ArrayList<UserOrder>(Arrays.asList(order)))
                .build();
        order.setUser(buyer);
        this.userRepository.save(buyer);
        product.setOrder(order);
        this.productRepository.save(product);
    }
}
