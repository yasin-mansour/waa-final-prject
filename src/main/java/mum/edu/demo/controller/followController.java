package mum.edu.demo.controller;

import mum.edu.demo.config.JPAUserDetails;
import mum.edu.demo.demain.Role;
import mum.edu.demo.demain.User;
import mum.edu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
public class followController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/follow/{id}")
    public String follow(@PathVariable Long id, HttpServletRequest request) {
        Optional<User> userOptional = userService.findById(id);
        Principal principal = request.getUserPrincipal();

        if(principal != null && userOptional.isPresent()){
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if(userBuyerOptional.isPresent()){
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                mum.edu.demo.demain.User seller = userOptional.get();
                buyer.follow(seller);
                seller.addFollower(buyer);
                userService.save(buyer);
            }
        }
        return "redirect:/sellers";
    }

    @RequestMapping(value = "/unfollow/{id}")
    public String unFollow(@PathVariable Long id, HttpServletRequest request) {
        Optional<User> userOptional = userService.findById(id);
        Principal principal = request.getUserPrincipal();

        if(principal != null && userOptional.isPresent()){
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if(userBuyerOptional.isPresent()){
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                mum.edu.demo.demain.User seller = userOptional.get();
                seller.getFollower().removeIf(user -> user.getEmail().equals(userDetails.getUsername()));
                buyer.getFollows().removeIf(user -> user.getId().equals(seller.getId()));
                userService.save(seller);
            }
        }
        return "redirect:/sellers";
    }
}
