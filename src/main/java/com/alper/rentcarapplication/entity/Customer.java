package com.alper.rentcarapplication.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer {
	@Column(name = "role")
	private String role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_surname")
    private String customerSurname;
    @NotBlank(message = "Email boş olamaz.")
    @Email(message = "Geçerli bir email adresi giriniz.")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;
    private String username;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @Column(name = "is_active")
    private Boolean isActive;
}

