package com.alper.rentcarapplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="car")
@Getter
@Setter
public class Car {	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    private Long id;
	@Column(name = "image_url")
	private String imageUrl;
    @Column(name = "car_name")
    private String carName;
    private Double price;
    private String model;
    private String brand;
    private int year;
    private String color;
    @Column(name = "fuel_type")
    private String fuelType;
    private String transmission;
     int seats;
    @Column(name = "license_plate")
    private String licensePlate;
}
	
