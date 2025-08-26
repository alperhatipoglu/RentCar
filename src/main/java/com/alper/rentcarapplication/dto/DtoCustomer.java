package com.alper.rentcarapplication.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DtoCustomer {
	 private Long id;
	    private String customerName;
	    private String customerSurname;
	    private String email;
	    private String phoneNumber;
	    private String username;
	    private LocalDate dateOfBirth;
	    private LocalDateTime registrationDate;
	    private Boolean isActive;
	    private String role;
	    @JsonIgnore
	    private String password;

}
