package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.CarGETResDto;
import com.warehouse.models.Car;
import com.warehouse.repositories.CarRepository;

@Service
public class CarService {

    final private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // this is where the repository takes the pageable object and returns queried objects grouped by page
    // notice you must call getContent() on the returned paginated results
    public CarGETResDto findAll(Pageable pageable, @RequestParam Optional<String> search) {
        long count;
        Iterable<Car> cars;
        if (search.isPresent()) {
            cars = carRepository.findAllByOwnerName(search.get(), pageable).getContent();
            count = carRepository.countByOwnerName(search.get());
        } else {
            cars = carRepository.findAll(pageable);
            count = carRepository.count();
        }
        CarGETResDto res = new CarGETResDto(cars, count, pageable);
        return res;
    }

    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void update(int id, Car car) {
        if (!carRepository.existsById(id)) 
            throw new NoSuchElementException("Car with id " + id + " does not exist");
        carRepository.save(car);
    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    public long count() {
        return carRepository.count();
    }
}
