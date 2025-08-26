package com.alper.rentcarapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alper.rentcarapplication.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	 boolean existsByUsername(String username);
	 boolean existsByEmail(String email);
	 boolean existsByPhoneNumber(String phoneNumber);
	 Optional<Customer> findByUsername(String username);

}
