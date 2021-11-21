package org.glsid3.securityservice;

import org.glsid3.securityservice.dto.AppRoleRequestDto;
import org.glsid3.securityservice.dto.AppUserRequestDto;
import org.glsid3.securityservice.entities.AppRole;
import org.glsid3.securityservice.entities.AppUser;
import org.glsid3.securityservice.sevices.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner start(AccountService accountService)
    {
        return args -> {
            accountService.addNewRole(new AppRoleRequestDto(null,"USER"));
            accountService.addNewRole(new AppRoleRequestDto(null,"ADMIN"));
            accountService.addNewRole(new AppRoleRequestDto(null,"CUSTOMER_MANAGER"));
            accountService.addNewRole(new AppRoleRequestDto(null,"PRODUCT_MANAGER"));
            accountService.addNewRole(new AppRoleRequestDto(null,"BILLS_MANAGER"));

            accountService.addNewUser(new AppUserRequestDto(null,"user1","1234", new ArrayList<>()));
            accountService.addNewUser(new AppUserRequestDto(null,"admin","1234", new ArrayList<>()));
            accountService.addNewUser(new AppUserRequestDto(null,"user3","1234", new ArrayList<>()));
            accountService.addNewUser(new AppUserRequestDto(null,"user4","1234", new ArrayList<>()));
            accountService.addNewUser(new AppUserRequestDto(null,"user5","1234", new ArrayList<>()));
            accountService.addNewUser(new AppUserRequestDto(null,"user6","1234", new ArrayList<>()));

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("admin","ADMIN");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("user3","CUSTOMER_MANAGER");
            accountService.addRoleToUser("user3","USER");
            accountService.addRoleToUser("user3","ADMIN");
            accountService.addRoleToUser("user4","PRODUCT_MANAGER");
            accountService.addRoleToUser("user5","BILLS_MANAGER");
            accountService.addRoleToUser("user6","USER");

        };
    }
}



