package com.alper.rentcarapplication.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alper.rentcarapplication.dto.DtoCustomer;
import com.alper.rentcarapplication.entity.Customer;
import com.alper.rentcarapplication.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	
	public DtoCustomer register(DtoCustomer dto) {
	 
	    if (dto.getUsername() == null || dto.getUsername().isBlank()) {
	        throw new IllegalArgumentException("Username boş olamaz.");
	    }
	    if (dto.getEmail() == null || dto.getEmail().isBlank()) {
	        throw new IllegalArgumentException("Email boş olamaz.");
	    }
	    String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	    if (!dto.getEmail().matches(emailPattern) ||
	        !(dto.getEmail().endsWith("@gmail.com") || dto.getEmail().endsWith("@outlook.com") || dto.getEmail().endsWith("@email.com"))) {
	        throw new IllegalArgumentException("Geçerli bir e-posta adresi giriniz.");
	    }

	    if (dto.getPassword() == null || dto.getPassword().isBlank()) {
	        throw new IllegalArgumentException("Password boş olamaz.");
	    }
	    if (dto.getPassword().length() < 6 || dto.getPassword().length() > 12) {
	        throw new IllegalArgumentException("Şifre 6 ile 12 karakter aralığında olmalıdır.");
	    }
	    if (customerRepository.existsByUsername(dto.getUsername())) {
	        throw new IllegalArgumentException("Kullanıcı adı mevcut.");
	    }
	    if (customerRepository.existsByEmail(dto.getEmail())) {
	        throw new IllegalArgumentException("Email zaten kayıtlı.");
	    }
	    if (customerRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
	        throw new IllegalArgumentException("Bu telefon numarası zaten kayıtlı.");
	    }
	    if (dto.getCustomerName() == null || dto.getCustomerName().isBlank()) {
	        throw new IllegalArgumentException("İsim boş olamaz.");
	    }
	    if (dto.getCustomerSurname() == null || dto.getCustomerSurname().isBlank()) {
	        throw new IllegalArgumentException("Soyisim boş olamaz.");
	    }
	    if (dto.getDateOfBirth() == null) {
	        throw new IllegalArgumentException("Doğum tarihi boş olamaz.");
	    }
	    String phonePattern = "^(\\+90|0)?5\\d{9}$";
	    if (!dto.getPhoneNumber().matches(phonePattern)) {
	        throw new IllegalArgumentException("Geçerli bir telefon numarası giriniz.");
	    }

	    Customer customer = new Customer();
	    BeanUtils.copyProperties(dto, customer);
	    customer.setPassword(passwordEncoder.encode(dto.getPassword()));
	    customer.setRole("ROLE_CUSTOMER");
	    customer.setRegistrationDate(LocalDateTime.now());
	    customer.setIsActive(true);

	    Customer savedCustomer = customerRepository.save(customer);

	    DtoCustomer result = new DtoCustomer();
	    BeanUtils.copyProperties(savedCustomer, result);
	    return result;
	}

    
    public DtoCustomer login(String username, String password) {
    	Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));
    	if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("Şifre hatalı.");
        }
    	 DtoCustomer dto = new DtoCustomer();
        BeanUtils.copyProperties(customer, dto);
        dto.setRole(customer.getRole()); 
        return dto;    
        }
    
    public DtoCustomer passwordchange(String username, String oldpassword, String newpassword) {
    	Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));
    	
    	if (!passwordEncoder.matches(oldpassword, customer.getPassword())) {
            throw new IllegalArgumentException("Eski şifre hatalı.");
    	}
    	if ( newpassword.length() < 6 || newpassword.length() > 12) {
    		throw new IllegalArgumentException("Şifre 6 ile 12 karakter aralığında olmalıdır.");            
    	}
    	    	
    	customer.setPassword(passwordEncoder.encode(newpassword));
        Customer updatedCustomer = customerRepository.save(customer);
    	
    	DtoCustomer dto = new DtoCustomer();
        BeanUtils.copyProperties(updatedCustomer, dto);
        return dto;
    }
    
    public DtoCustomer getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));
        DtoCustomer dto = new DtoCustomer();
        BeanUtils.copyProperties(customer, dto);
        dto.setRole(customer.getRole());
        return dto;
    }

}