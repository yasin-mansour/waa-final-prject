package mum.edu.demo.excecption;

import javax.servlet.http.HttpServletRequest;

import mum.edu.demo.excecption.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleError(HttpServletRequest req, NotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", exception.getMessage());
        mav.addObject("hasError", true);
        mav.setViewName("notFound");
        return mav;
    }

}
