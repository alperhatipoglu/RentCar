package com.alper.rentcarapplication.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alper.rentcarapplication.dto.DtoCustomer;
import com.alper.rentcarapplication.security.JwtUtil;
import com.alper.rentcarapplication.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	

	@PostMapping("/register")
	public DtoCustomer register(@Valid @RequestBody DtoCustomer dto) {
	    return customerService.register(dto);
	}

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
	    String username = loginData.get("username");
	    String password = loginData.get("password");

	    DtoCustomer customer = customerService.login(username, password);
	    String token = jwtUtil.generateToken(username, customer.getRole()); 

	    return Map.of(
	        "token", token,
	        "user", customer
	    );
	}



	@PostMapping("/passwordchange")
	public DtoCustomer passwordchange(@AuthenticationPrincipal UserDetails principal,
	                                  @RequestParam String oldpassword,
	                                  @RequestParam String newpassword) {
	    return customerService.passwordchange(principal.getUsername(), oldpassword, newpassword);
	}
	
	
	@GetMapping("/{id}")
    public DtoCustomer getProfile(@PathVariable Long id) {
        return customerService.getCustomerById(id); 
    }

}