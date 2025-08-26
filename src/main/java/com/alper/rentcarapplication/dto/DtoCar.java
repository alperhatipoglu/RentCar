package com.alper.rentcarapplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCar {
	private Long id;
	private String carName;
	private String imageUrl;
    private Double price;
    private String model;
    private String brand;
    private int year;
    private String color;
    private String fuelType;
    private String transmission;
    private int seats;
    private String licensePlate;
}
