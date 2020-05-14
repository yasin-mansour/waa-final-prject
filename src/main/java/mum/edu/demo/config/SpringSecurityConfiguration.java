package mum.edu.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Qualifier("JPAUserDetailService")
    @Autowired
    UserDetailsService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/seller/{id}/active").hasRole("ADMIN")
                .antMatchers("/seller/product").hasRole("SELLER")
                .antMatchers("/orders").hasRole("BUYER")
                .antMatchers("/cart").hasRole("BUYER")
                .antMatchers("/orders/{id}/pdf").hasRole("BUYER")
                .antMatchers("/carts/**").hasRole("BUYER")
                .antMatchers("/follow/**").hasRole("BUYER")
                .antMatchers("/unfollow/**").hasRole("BUYER")
                .antMatchers("/orders/{id}/remove").hasRole("BUYER")
                .antMatchers("/orders/{id}/status").hasRole("SELLER")
                .antMatchers("/ordersList").hasRole("SELLER")
                .antMatchers("/products/**").hasRole("SELLER")
                .antMatchers("/products").hasRole("SELLER")
                .antMatchers("/","/login", "/h2-console/**").permitAll().and()
                .formLogin()
                .loginPage("/login")
//                    .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") //change default /logout url to /perform_logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied");;

        //Those two settings below is to enable access h2 database via browser
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
