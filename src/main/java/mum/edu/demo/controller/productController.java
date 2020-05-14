package mum.edu.demo.controller;

import mum.edu.demo.config.JPAUserDetails;
import mum.edu.demo.demain.Product;
import mum.edu.demo.demain.Role;
import mum.edu.demo.excecption.NotFoundException;
import mum.edu.demo.service.UserService;
import org.springframework.security.core.userdetails.User;
import mum.edu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.security.Principal;
import java.util.Optional;

@Controller
public class productController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping("/seller/product")
    public String getProductView(@ModelAttribute Product product) {
        return "product";
    }


    @RequestMapping(value = "products/{id}/edit")
    public String getEditProductView(@PathVariable int id, Model model) {
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent()){
            model.addAttribute("product", productOptional.get());
        } else {
            throw new NotFoundException("No Product has been found with Id " + id);
        }

        return "product";
    }

    @RequestMapping(value = "products/{id}/delete")
    public String DeleteProductView(@PathVariable int id, Model model) {
       productService.deleteById(id);

        return "redirect:/";
    }


    @RequestMapping(value = "products")
    public String saveProduct(@Valid Product product,BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "product";
        }
        MultipartFile image = product.getImage();
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            Authentication authentication = (Authentication) principal;
            JPAUserDetails user = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userObject = userService.findByEmail(user.getUsername());
            if(userObject.isPresent()){
                mum.edu.demo.demain.User seller = userObject.get();
                seller.addProduct(product);
                product.setOwner(seller);
                product = productService.save(product);
                String uploadsDir = "/images/";
                String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                if(! new File(realPathtoUploads).exists())
                {
                    new File(realPathtoUploads).mkdir();
                }
                if (image != null && !image.isEmpty()) {
                    try {
                        image.transferTo(new File(realPathtoUploads  + product.getId() + ".png"));
                    } catch (Exception e) {
                        throw new NotFoundException(e.getMessage());
                    }
                }
            }

        }

        return "redirect:/";
    }

    @RequestMapping(value = "/carts/{productId}")
    public String addToCard(@PathVariable int productId, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userOptional = userService.findByEmail(userDetails.getUsername());
            Optional<Product> productOptional = productService.findById(productId);
            if(userOptional.isPresent() && productOptional.isPresent()){
                mum.edu.demo.demain.User user = userOptional.get();
                Product product = productOptional.get();
                user.addToCart(product);
                product.addToCart(user);
                userService.save(user);

            }
        }

        return "redirect:/";
    }


    @RequestMapping(value = "/carts/{productId}/remove")
    public String removeFromCard(@PathVariable int productId, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            Authentication authentication = (Authentication) principal;
            JPAUserDetails userDetails = (JPAUserDetails) authentication.getPrincipal();
            Optional<mum.edu.demo.demain.User> userOptional = userService.findByEmail(userDetails.getUsername());
            //Optional<Product> productOptional = productService.findById(productId);
            if(userOptional.isPresent()){
                mum.edu.demo.demain.User user = userOptional.get();
               user.getCart().removeIf(product-> product.getId() == productId);
                userService.save(user);

            }
        }

        return "redirect:/";
    }

}
