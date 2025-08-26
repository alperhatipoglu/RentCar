package com.alper.rentcarapplication.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DtoRent {
	 private Long id;          
	    private Long customerId;  
	    private Long carId;       
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private Double totalPrice;
	    private Boolean isActive;
	    private String paymentStatus;
	    private String paymentMethodExpiry; 
	    private String carName;
	    private String customerName;
	    private String paymentMethod;
	    private String customerSurname;
	    private String message;
	    private String customerEmail; 
	    private String customerAddress;
	    private String cardName;
	    private String cvv;
	    private String cardNumber; 

}
