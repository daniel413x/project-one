package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.CarDto;
import com.warehouse.dtos.CarGETResDto;
import com.warehouse.dtos.CarPOSTDto;
import com.warehouse.dtos.CarPUTDto;
import com.warehouse.models.Car;
import com.warehouse.models.Color;
import com.warehouse.models.Make;
import com.warehouse.models.Model;
import com.warehouse.models.Owner;
import com.warehouse.repositories.CarRepository;

@Service
public class CarService {

    final private CarRepository carRepository;

    final private MakeService makeService;

    final private ModelService modelService;

    final private OwnerService ownerService;

    final private ColorService colorService;

    public CarService(CarRepository carRepository, MakeService makeService, ModelService modelService, OwnerService ownerService, ColorService colorService) {
        this.carRepository = carRepository;
        this.makeService = makeService;
        this.modelService = modelService;
        this.ownerService = ownerService;
        this.colorService = colorService;
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

// TODO: separate from CarService
    private Car carMapper(Car car, CarPOSTDto carForm) {
        Model model = modelService.findById(carForm.getModelId())
                .orElseThrow(() -> new RuntimeException("Model not found"));
        Make make = makeService.findById(carForm.getMakeId())
                .orElseThrow(() -> new RuntimeException("Make not found"));
        Owner owner = ownerService.findById(carForm.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Color color = colorService.findById(carForm.getColorId())
                .orElseThrow(() -> new RuntimeException("Color not found"));
        car.setColor(color);
        car.setOwner(owner);
        car.setMake(make);
        car.setModel(model);
        car.setRegistrationNumber(carForm.getRegistrationNumber());
        car.setInsuranceExpiration(carForm.getInsuranceExpiration());
        car.setRegistrationExpiration(carForm.getRegistrationExpiration());
        car.setInsurancePolicyNumber(carForm.getInsurancePolicyNumber());
        car.setLastMaintenanceDate(carForm.getLastMaintenanceDate());
        car.setMileage(carForm.getMileage());
        car.setPrice(carForm.getPrice());
        car.setVin(carForm.getVin());
        car.setYear(carForm.getYear());
        return car;
    }

    public CarDto save(CarPOSTDto carForm) {
        Car car = new Car();
        car = carMapper(car, carForm);
        CarDto carDto = new CarDto(carRepository.save(car));
        return carDto;
    }

    public void update(int id, CarPUTDto carForm) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            Car updatedCar = carMapper(car.get(), carForm);
            carRepository.save(updatedCar);
        }
        if (!carRepository.existsById(id)) 
            throw new NoSuchElementException("Car with id " + id + " does not exist");
    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    public long count() {
        return carRepository.count();
    }
}
