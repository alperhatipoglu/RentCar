package com.alper.rentcarapplication.config;

import java.time.LocalDateTime;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alper.rentcarapplication.entity.Customer;
import com.alper.rentcarapplication.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @PostConstruct
    public void init() {
        Customer admin = customerRepository.findByUsername(adminUsername).orElse(null);

        if (admin == null) {
            admin = new Customer();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setCustomerName("Admin");
            admin.setCustomerSurname("User");
            admin.setRole("ROLE_ADMIN");
            admin.setIsActive(true);
            admin.setRegistrationDate(LocalDateTime.now());

            System.out.println("Yeni admin oluşturuluyor.");
        } else {
            System.out.println("Mevcut admin bulundu, şifre güncellenecek.");
        }

        admin.setPassword(passwordEncoder.encode(adminPassword));
        customerRepository.save(admin);
        System.out.println("Admin şifresi güncellendi veya admin oluşturuldu.");
    }

}
