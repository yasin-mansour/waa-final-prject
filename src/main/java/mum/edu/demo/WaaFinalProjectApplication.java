package mum.edu.demo;

import mum.edu.demo.seeds.ApplicationSeeds;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WaaFinalProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app =  SpringApplication.run(WaaFinalProjectApplication.class, args);
        ApplicationSeeds applicationSeeds = app.getBean(ApplicationSeeds.class);
        applicationSeeds.init();
    }

}
