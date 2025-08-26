package com.alper.rentcarapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alper.rentcarapplication.dto.DtoRent;
import com.alper.rentcarapplication.service.AdminRentService;

@RestController
@RequestMapping("/admin/rents")  
@PreAuthorize("hasRole('ADMIN')")  
public class AdminRentController {

    private final AdminRentService adminRentService;

    @Autowired
    public AdminRentController(AdminRentService adminRentService) {
        this.adminRentService = adminRentService;
    }

    @GetMapping
    public List<DtoRent> getAllRents() {
        return adminRentService.getAllRents();
    }

    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable Long id) {
        adminRentService.deleteRentById(id);
    }
}
