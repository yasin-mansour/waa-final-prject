package mum.edu.demo.controller;

import mum.edu.demo.demain.Role;
import mum.edu.demo.demain.User;
import mum.edu.demo.service.RoleService;
import mum.edu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class userController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/seller/register")
    public String getRegisterView(@ModelAttribute User user, Model model) {
        model.addAttribute("type", "seller");
        return "register";
    }

    @GetMapping("/buyer/register")
    public String getBuyerRegisterView(@ModelAttribute User user, Model model) {
        model.addAttribute("type", "buyer");
        return "register";
    }

    @RequestMapping(value = "/seller")
    public String saveSeller(@Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        Optional<Role> role = roleService.findByRole("ROLE_SELLER");
        if (role.isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(role.get());
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(1);
            userService.save(user);
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/buyer")
    public String saveBuyer(@Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        Optional<Role> role = roleService.findByRole("ROLE_BUYER");
        if (role.isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(role.get());
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(1);
            user.setMoney(500);
            userService.save(user);
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/seller/{id}/active")
    public String activeSeller(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            if(!user.get().canAddProduct()){
                User seller = user.get();
                Optional<Role> role = roleService.findByRole("ROLE_PRODUCT");
                if(role.isPresent()){
                    seller.addRole(role.get());
                    userService.save(seller);
                }
            }
        }

       return "redirect:/sellers";
    }
}
