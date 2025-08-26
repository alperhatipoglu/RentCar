package com.alper.rentcarapplication.security;

import com.alper.rentcarapplication.entity.Customer;
import com.alper.rentcarapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(customer.getRole());

        return new User(
            customer.getUsername(),
            customer.getPassword(),
            Collections.singletonList(authority)
        );
    }
}
