package com.alper.rentcarapplication.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alper.rentcarapplication.dto.DtoCar;
import com.alper.rentcarapplication.entity.Car;
import com.alper.rentcarapplication.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class CarService {
	
	@Autowired
    private CarRepository carRepository;
    public List<DtoCar> getAllCars() {
    	
        List<Car> cars = carRepository.findAll();
        List<DtoCar> dtoCarList = new ArrayList<>();
        for (Car car : cars) {
            DtoCar dto = new DtoCar();
            BeanUtils.copyProperties(car, dto);
            dtoCarList.add(dto);
        }
        return dtoCarList;
    }
    public DtoCar createCar(DtoCar dtoCar) {
    	if (dtoCar.getPrice() != null && dtoCar.getPrice() < 0) {
            throw new IllegalArgumentException("It cannot be empty or less than 0!");
    	}
    	if (dtoCar.getBrand() == null || dtoCar.getBrand().isBlank() || dtoCar.getCarName() == null || dtoCar.getCarName().isBlank()) {
    		    throw new IllegalArgumentException("Brand and Name cannot be empty!");
    		}
    	Car car = new Car();
        BeanUtils.copyProperties(dtoCar, car);

        Car savedCar = carRepository.save(car);

        DtoCar resultDto = new DtoCar();
        BeanUtils.copyProperties(savedCar, resultDto);
        return resultDto;
    }

    public DtoCar getCarById(Long id) {
        Car car = carRepository.findById(id)
        	    .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        DtoCar dto = new DtoCar();
        BeanUtils.copyProperties(car, dto);
        return dto;
    }

    public void deleteCar(Long id) {
    	if (!carRepository.existsById(id)) {
    	    throw new EntityNotFoundException("Car not found with id: " + id);
    	}
        carRepository.deleteById(id);
    }
    
    public DtoCar updateOneCar(Long id, DtoCar dtoCar) {
    	  Car existingCar = carRepository.findById(id)
    	            .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));

    	        BeanUtils.copyProperties(dtoCar, existingCar, "id"); 
    	        Car updatedCar = carRepository.save(existingCar);

    	        DtoCar resultDto = new DtoCar();
    	        BeanUtils.copyProperties(updatedCar, resultDto);
    	        return resultDto;
    }
}
