package com.alper.rentcarapplication.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alper.rentcarapplication.dto.DtoRent;
import com.alper.rentcarapplication.entity.Car;
import com.alper.rentcarapplication.service.RentService;

@RestController
@RequestMapping("/rent")
public class RentController {
	@Autowired
	private RentService rentService;
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/create")
	public DtoRent createRent(@RequestBody DtoRent dtoRent) {
        return rentService.createRent(dtoRent);
	    
	}

@GetMapping("/available")
public List<Car> getAvailableCars(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
	return rentService.getAvailableCars(startDate, endDate);
    }

@PreAuthorize("hasRole('CUSTOMER')")
@DeleteMapping("/cancel/{id}")
public void cancelRent(@PathVariable Long id) {
    rentService.cancelRent(id);
}


}
