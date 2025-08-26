package com.alper.rentcarapplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alper.rentcarapplication.entity.Rent;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate endDate, LocalDate startDate);

}



