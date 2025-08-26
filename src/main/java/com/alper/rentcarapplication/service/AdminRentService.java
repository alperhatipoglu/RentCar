package com.alper.rentcarapplication.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alper.rentcarapplication.dto.DtoRent;
import com.alper.rentcarapplication.entity.Rent;
import com.alper.rentcarapplication.repository.RentRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRentService {
    @Autowired
    private RentRepository rentRepository;

    @Transactional
    public List<DtoRent> getAllRents() {
    	 List<Rent> rents = rentRepository.findAll();
         List<DtoRent> dtoList = new ArrayList<>();

         for (Rent rent : rents) {
             DtoRent dto = new DtoRent();
             BeanUtils.copyProperties(rent, dto);
             dto.setCarId(rent.getCar().getId());
             dto.setCustomerId(rent.getCustomer().getId());
             dtoList.add(dto);
         }

         return dtoList;
    }

    @Transactional
    public void deleteRentById(Long id) {
        rentRepository.deleteById(id);
    }
}
