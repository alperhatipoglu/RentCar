package com.alper.rentcarapplication.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alper.rentcarapplication.dto.DtoRent;
import com.alper.rentcarapplication.entity.Car;
import com.alper.rentcarapplication.entity.Customer;
import com.alper.rentcarapplication.entity.Rent;
import com.alper.rentcarapplication.repository.CarRepository;
import com.alper.rentcarapplication.repository.CustomerRepository;
import com.alper.rentcarapplication.repository.RentRepository;

import jakarta.transaction.Transactional;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CarRepository carRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public DtoRent createRent(DtoRent dto) 
    
    
    {
    	
    	
    	 if (dto.getStartDate() == null || dto.getEndDate() == null || !dto.getEndDate().isAfter(dto.getStartDate())) {
             throw new IllegalArgumentException("Geçersiz tarih aralığı. endDate, startDate'den sonra olmalıdır.");
         }
    	 
    	  if (dto.getCustomerAddress() == null || dto.getCustomerAddress().trim().isEmpty()) {
    	        throw new IllegalArgumentException("Adres bilgisi zorunludur.");
    	    }

        List<Rent> overlappingRents = rentRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(dto.getEndDate(), dto.getStartDate());

        boolean carIsRented = overlappingRents.stream()
                .anyMatch(r -> r.getCar().getId().equals(dto.getCarId()) && Boolean.TRUE.equals(r.getIsActive()));

        if (carIsRented) {
            throw new IllegalStateException("Seçilen araç bu tarih aralığında zaten kiralanmış.");
        }

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Araç bulunamadı"));
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Müşteri bulunamadı"));
        
        if (!customer.getEmail().equalsIgnoreCase(dto.getCustomerEmail())) {
            throw new IllegalStateException("Emailinizi tekrar kontrol ediniz!");
        }

               
        
        if (dto.getPaymentMethod() == null || dto.getPaymentMethod().trim().isEmpty() || dto.getPaymentMethod().startsWith("@")) {
            throw new IllegalArgumentException("Geçersiz ödeme yöntemi.");
        }
        

     String expiry = dto.getPaymentMethodExpiry(); 
     if (expiry != null && !expiry.isBlank()) {
         try {
             String[] parts = expiry.split("/");
             if(parts.length != 2) throw new IllegalArgumentException();
             int month = Integer.parseInt(parts[0]);
             int year = Integer.parseInt(parts[1]) + 2000;
             if (month < 1 || month > 12) throw new IllegalArgumentException("Geçersiz ay.");

             YearMonth cardExpiry = YearMonth.of(year, month);
             YearMonth now = YearMonth.now();

             if (cardExpiry.isBefore(now)) {
                 throw new IllegalArgumentException("Kartın son kullanma tarihi geçmiş.");
             }

         } catch (Exception e) {
             throw new IllegalArgumentException("Kart son kullanma tarihi geçersiz formatta.");
         }
     } else {
         throw new IllegalArgumentException("Kart son kullanma tarihi zorunludur.");
     } 
     
     
     if (dto.getCardName() == null || dto.getCardName().trim().isEmpty()) {
         throw new IllegalArgumentException("Kart sahibinin adı zorunludur.");
     }
     if (dto.getCvv() == null || !dto.getCvv().matches("\\d{3,4}")) {
         throw new IllegalArgumentException("CVV geçersiz veya boş bırakılamaz.");
     }
     
     if (dto.getCardNumber() == null || dto.getCardNumber().trim().isEmpty()) {
    	    throw new IllegalArgumentException("Kart numarası zorunludur.");
    	}

    if (!dto.getCardNumber().matches("\\d{16}")) {
    	   throw new IllegalArgumentException("Kart numarası 16 haneli olmalıdır.");
    	}

        Rent rent = new Rent();
        BeanUtils.copyProperties(dto, rent, "cardName", "cvv", "cardNumber"); 
        rent.setCar(car);
        rent.setCustomer(customer);
        rent.setIsActive(true); 
        rent.setPaymentMethod(dto.getPaymentMethod());
        rent.setCustomerName(customer.getCustomerName());
        rent.setCustomerSurname(customer.getCustomerSurname());
        rent.setCarName(car.getCarName());
        rent.setCustomerAddress(dto.getCustomerAddress()); 


        
        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        double totalPrice = car.getPrice() * days;
        rent.setTotalPrice(totalPrice);
        
        
        if (!simulatePayment(customer, totalPrice, dto.getPaymentMethod(), dto.getCardName(), dto.getCvv())) {
            throw new IllegalStateException("Ödeme başarısız! Kiralama oluşturulamadı.");
        }
        
        Rent savedRent = rentRepository.save(rent);
                

        DtoRent result = new DtoRent();
        BeanUtils.copyProperties(savedRent, result); 
        result.setCarId(savedRent.getCar().getId());
        result.setCustomerId(savedRent.getCustomer().getId());
        result.setMessage("Kiralama başarıyla oluşturuldu");

        return result;    
        }
    
    
    private boolean simulatePayment(Customer customer, double amount, String paymentMethod, String cardName, String cvv) {
        System.out.println("Ödeme alındı: " + amount + " TL, Yöntem: " + paymentMethod + ", Kart Sahibi: " + cardName);
        return true; 
    }
        

    public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        List<Rent> overlappingRents = rentRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(endDate, startDate);

        List<Long> rentedCarIds = overlappingRents.stream()
                .map(r -> r.getCar().getId())
                .distinct()
                .collect(Collectors.toList());

        return carRepository.findAll().stream()
                .filter(car -> !rentedCarIds.contains(car.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelRent(Long id) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kiralamaya ait kayıt bulunamadı"));

        rent.setIsActive(false);
        rentRepository.save(rent);
    }
}
