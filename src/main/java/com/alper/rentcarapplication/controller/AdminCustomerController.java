package com.alper.rentcarapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alper.rentcarapplication.dto.DtoCustomer;

import com.alper.rentcarapplication.service.AdminCustomerService;

@RestController
	@RequestMapping("/admin/customers")
@PreAuthorize("hasRole('ADMIN')")

	public class AdminCustomerController {

	@Autowired
    private AdminCustomerService adminCustomerService;

    @GetMapping
    public List<DtoCustomer> getAllCustomers() {
        return adminCustomerService.getAllCustomers();
    }


    @PutMapping("/{id}/deactivate")
    public DtoCustomer deactivateCustomer(@PathVariable Long id) {
        return adminCustomerService.deactivateCustomer(id);
    }

    @PutMapping("/{id}/activate")
    public DtoCustomer activateCustomer(@PathVariable Long id) {
        return adminCustomerService.activateCustomer(id);
    }

    @DeleteMapping("/{id}")
    public DtoCustomer deleteCustomer(@PathVariable Long id) {
        return adminCustomerService.deleteCustomer(id);
    }
}
