package mum.edu.demo.controller;

import mum.edu.demo.config.JPAUserDetails;
import mum.edu.demo.demain.Product;
import mum.edu.demo.demain.Review;
import mum.edu.demo.demain.User;
import mum.edu.demo.demain.UserOrder;
import mum.edu.demo.excecption.NotFoundException;
import mum.edu.demo.service.ProductService;
import mum.edu.demo.service.ReviewService;
import mum.edu.demo.service.UserOrderService;
import mum.edu.demo.service.UserService;
import mum.edu.demo.util.GeneratePdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class orderController {

    @Autowired
    UserService userService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    ProductService productService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/orders")
    public String getOrderView(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if (userBuyerOptional.isPresent()) {
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                model.addAttribute("orders", buyer.getOrders());

            }
        }

        return "orders";
    }

    @GetMapping("/orders/{id}/review")
    public String getReviewView(@PathVariable int id, @ModelAttribute Review review, Model model) {
        model.addAttribute("orderId", id);
        return "review";
    }

    @RequestMapping("/orders/{id}/review")
    public String addReview(@Valid Review review, BindingResult bindingResult, @PathVariable int id, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "/orders/" + id + "/review";
        }

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if (userBuyerOptional.isPresent()) {
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                Optional<UserOrder> orderOptional = buyer.getOrders().stream().filter(order -> order.getId() == id).findFirst();

                if (orderOptional.isPresent()) {
                    UserOrder order = orderOptional.get();
                    order.setReview(review);
                    review.setOrder(order);
                    reviewService.save(review);
                    buyer.setPoints(buyer.getPoints() + 100);
                    userService.save(buyer);
                }

            }
        }
        return "redirect:/orders";
    }

    @GetMapping("/ordersList")
    public String getOrderListView(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if (userBuyerOptional.isPresent()) {
                mum.edu.demo.demain.User seller = userBuyerOptional.get();
                model.addAttribute("orders", userOrderService.getOrdersBySeller(seller.getId()));
            }
        }

        return "ordersList";
    }

    @GetMapping("/admin/orders")
    public String getAdminOrdersView(Model model) {
        model.addAttribute("orders", userOrderService.findAll());
        return "orders";
    }

    @RequestMapping(value = "/admin/review/approve")
    public String activeReview(Review review) {
        Optional<Review> reviewOptional = reviewService.findById(review.getId());
        if (reviewOptional.isPresent()) {
            review = reviewOptional.get();
            review.setActive(true);
            reviewService.save(review);
        }

        return "redirect:/admin/orders";
    }


    @GetMapping("/seller/{id}/reviews")
    public String getSellersView(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("reviews", reviewService.getSellerReviews(id));
        } else {
            throw new NotFoundException("No Seller has been found with Id " + id);
        }

        return "sellerReviews";
    }

    @RequestMapping(value = "/order")
    public String order(@Valid @ModelAttribute UserOrder userOrder, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userOrder", userOrder);
            redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
            return "redirect:/cart";
        }

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if (userBuyerOptional.isPresent()) {
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                List<Product> products = buyer.getCart();
                int totalPrice = buyer.getCartTotalPrice();
                if (totalPrice < buyer.getTotalMoney()) {
                    buyer.setCart(new ArrayList<>());
                    buyer.withdrawMoney(totalPrice);
                    products.stream().forEach(product -> {
                        if (!product.isSold()) {
                            try {
                                UserOrder orderClone = (UserOrder) userOrder.clone();
                                orderClone.setProduct(product);
                                orderClone.setUser(buyer);
                                userOrderService.save(orderClone);
                                product.setOrder(orderClone);
                                buyer.addOrder(orderClone);
                            } catch (CloneNotSupportedException e) {
                                throw new NotFoundException("Unknown Exception");
                            }
                        }
                    });
                    userService.save(buyer);
                } else {
                    throw new NotFoundException("You do not have Enough Money");
                }

            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/orders/{id}/status")
    public String order(@ModelAttribute UserOrder order, @PathVariable int id) {
        Optional<UserOrder> orderOptional = userOrderService.findById(id);
        if (orderOptional.isPresent()) {
            UserOrder orderOld = orderOptional.get();
            if (order.getStatus().equals("CANCELED")) {
                User buyer = orderOld.getUser();
                buyer.setMoney(orderOld.getProduct().getPrice() + buyer.getMoney());
                userService.save(buyer);
            } else {
                Product product = orderOld.getProduct();
                User seller = product.getOwner();
                seller.setMoney(seller.getMoney() + product.getPrice());
                userService.save(seller);
                product.setSold(true);
                productService.save(product);
            }
            orderOld.setStatus(order.getStatus());
            userOrderService.save(orderOld);
        }
        return "redirect:/ordersList";
    }

    @RequestMapping(value = "/orders/{id}/remove")
    public String removeOrder(@PathVariable int id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if (userBuyerOptional.isPresent()) {
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                Optional<UserOrder> orderOptional = buyer.getOrders().stream().filter(order -> order.getId() == id).findFirst();

                if (orderOptional.isPresent()) {
                    UserOrder order = orderOptional.get();
                    Product product = order.getProduct();
                    buyer.setMoney(product.getPrice() + buyer.getMoney());
                    product.setOrder(null);
                    productService.save(product);
                    order.setUser(null);
                    userOrderService.delete(order);
                    buyer.getOrders().remove(order);
                    userService.save(buyer);
                }

            }
        }
        return "redirect:/orders";
    }

    @RequestMapping(value = "/orders/{id}/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport(@PathVariable int id) {

        Optional<UserOrder> order = userOrderService.findById(id);
        if (order.isPresent()) {
            ByteArrayInputStream bis = GeneratePdfReport.citiesReport(order.get());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } else {
            throw new NotFoundException("Order Not Found");
        }


    }
}
