package com.alper.rentcarapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alper.rentcarapplication.dto.DtoCar;
import com.alper.rentcarapplication.service.CarService;


@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<DtoCar> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public DtoCar getOneCar(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DtoCar createCar(@RequestBody DtoCar dtoCar) {
        return carService.createCar(dtoCar);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DtoCar updateOneCar(@PathVariable Long id, @RequestBody DtoCar dtoCar) {
        return carService.updateOneCar(id, dtoCar);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
