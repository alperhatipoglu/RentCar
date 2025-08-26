package com.alper.rentcarapplication.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="rent")
@Getter
@Setter
public class Rent {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @ManyToOne
	 @JoinColumn(name = "customer_id", nullable = false)
	 private Customer customer;

	 @ManyToOne
	 @JoinColumn(name = "car_id", nullable = false)
	 private Car car;

	 @Column(name = "start_date", nullable = false)
	 private LocalDate startDate;

	 @Column(name = "end_date", nullable = false)
	 private LocalDate endDate;

	 @Column(name = "total_price")
	 private Double totalPrice;
	 
	 @Column(name = "customer_address", length = 200)
	 private String customerAddress;


	 @Column(name = "is_active")
	 private Boolean isActive;
	 
	 @Column(name = "payment_method")
	 private String paymentMethod; 
	 
	 @Column(name = "car_name")
	 private String carName;
	 
	 @Column(name = "customer_name")
	 private String customerName;
	 @Column(name = "customer_surname")
	 private String customerSurname;
	 
	 @PrePersist
		public void prePersist() {
			if (isActive == null) {
				isActive = true;
			}
		}

		public boolean isValidDates() {
			return startDate != null && endDate != null && endDate.isAfter(startDate);
		}
}
