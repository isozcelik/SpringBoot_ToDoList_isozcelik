package com.isozcelik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//exclude:dahil etmemek
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
}
)

public class SpringBootToDoListIsozcelikApplication {

    public static void main(String[] args) {
        //DevTools
        System.setProperty("spring.devtools.restart.enabled","false");

        // AWT: JOptionPanel set ayarı
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(SpringBootToDoListIsozcelikApplication.class, args);
    }

}
