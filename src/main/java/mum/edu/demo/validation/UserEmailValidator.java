package mum.edu.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import mum.edu.demo.demain.User;
import mum.edu.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserEmailValidator implements ConstraintValidator<UserEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserEmail arg0) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      User user = null;
        try {
            Optional<User> userOptional = userService.findByEmail(value);
            if(userOptional.isPresent()){
                user = userOptional.get();
            }
        } catch (Exception e) {
            System.out.println("Couldn't find product...");
        }
        return user == null ? true : false;
    }

}
