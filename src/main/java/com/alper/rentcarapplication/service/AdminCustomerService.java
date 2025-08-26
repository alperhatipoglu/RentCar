package com.alper.rentcarapplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alper.rentcarapplication.dto.DtoCustomer;
import com.alper.rentcarapplication.entity.Customer;
import com.alper.rentcarapplication.repository.CustomerRepository;

@Service
public class AdminCustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    private DtoCustomer convertToDto(Customer customer) {
        DtoCustomer dto = new DtoCustomer();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

    public List<DtoCustomer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<DtoCustomer> dtos = new ArrayList<>();
        for (Customer customer : customers) {
            dtos.add(convertToDto(customer));
        }
        return dtos;
    }

    public DtoCustomer deactivateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Müşteri bulunamadı"));
        customer.setIsActive(false);
        Customer updated = customerRepository.save(customer);
        return convertToDto(updated);
    }

    public DtoCustomer activateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Müşteri bulunamadı"));
        customer.setIsActive(true);
        Customer updated = customerRepository.save(customer);
        return convertToDto(updated);
    }

    public DtoCustomer deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Müşteri bulunamadı"));
        
        DtoCustomer dto = convertToDto(customer); 
        customerRepository.delete(customer);
        
        return dto; 
    }
}
