package mum.edu.demo.controller;

import mum.edu.demo.demain.Product;
import mum.edu.demo.demain.User;
import mum.edu.demo.service.ProductService;
import mum.edu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class homeController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getHomeView(@ModelAttribute User user, Model model) {
        List<Product> products = productService.findNotSold();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/sellers")
    public String getSellersView(Model model) {
        List<User> sellers = userService.getSellers();
        model.addAttribute("sellers", sellers);
        return "sellersList";
    }

}


