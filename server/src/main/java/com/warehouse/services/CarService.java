package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Iterable<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable).getContent();
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
