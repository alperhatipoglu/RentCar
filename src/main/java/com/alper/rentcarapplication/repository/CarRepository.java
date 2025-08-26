package com.alper.rentcarapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alper.rentcarapplication.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}

