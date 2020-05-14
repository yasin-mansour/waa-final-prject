package mum.edu.demo.controller;

import mum.edu.demo.config.JPAUserDetails;
import mum.edu.demo.demain.Product;
import mum.edu.demo.demain.User;
import mum.edu.demo.demain.UserOrder;
import mum.edu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class cartController {

    @Autowired
    UserService userService;

    @GetMapping("/cart")
    public String getHomeView(@ModelAttribute UserOrder userOrder, Model model, HttpServletRequest request) {

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            userOrder = (UserOrder) inputFlashMap.get("userOrder");
            BindingResult bindingResult = (BindingResult) inputFlashMap.get("bindingResult");
            model.addAttribute("userOrder", userOrder);
            model.addAttribute("org.springframework.validation.BindingResult.userOrder", bindingResult);
        }
        Principal principal = request.getUserPrincipal();

        if(principal != null){
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<User> userBuyerOptional = userService.findByEmail(userDetails.getUsername());
            if(userBuyerOptional.isPresent()){
                mum.edu.demo.demain.User buyer = userBuyerOptional.get();
                model.addAttribute("products", buyer.getCart());
            }
        }
        return "cart";
    }
}
