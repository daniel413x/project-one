package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.car.CarDto;
import com.warehouse.dtos.car.CarGETResDto;
import com.warehouse.dtos.car.CarPOSTDto;
import com.warehouse.dtos.car.CarPUTDto;
import com.warehouse.models.Car;
import com.warehouse.models.Color;
import com.warehouse.models.Make;
import com.warehouse.models.Model;
import com.warehouse.models.Owner;
import com.warehouse.repositories.CarRepository;
import com.warehouse.repositories.ColorRepository;
import com.warehouse.repositories.MakeRepository;
import com.warehouse.repositories.ModelRepository;
import com.warehouse.repositories.OwnerRepository;

@Service
public class CarService {

    final private CarRepository carRepository;

    final private MakeRepository makeRepository;

    final private ModelRepository modelRepository;

    final private OwnerRepository ownerRepository;

    final private ColorRepository colorRepository;

    public CarService(CarRepository carRepository, MakeRepository makeRepository, ModelRepository modelRepository, OwnerRepository ownerRepository, ColorRepository colorRepository) {
        this.carRepository = carRepository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.ownerRepository = ownerRepository;
        this.colorRepository = colorRepository;
    }

    // this is where the repository takes the pageable object and returns queried objects grouped by page
    // notice you must call getContent() on the returned paginated results
    public CarGETResDto findAll(Pageable pageable, @RequestParam Optional<String> search) {
        long count;
        Iterable<Car> cars;
        // the query contains ?search=
        if (search.isPresent()) {
            String searchString = search.get();
            String[] splitSearch = searchString.split("\\s-");
            // if splitSearch.length == 2, it appears the user is trying to search by setting a flag
            // search by the flag if it is valid, otherwise query-search by make name
            if (splitSearch.length == 2) {
                String flag = splitSearch[1];
                searchString = splitSearch[0];
                if (flag.equalsIgnoreCase("owner")) {
                    cars = carRepository.findByOwnerNameContainingIgnoreCase(searchString, pageable).getContent();
                    count = carRepository.countByOwnerNameContainingIgnoreCase(searchString);
                } else if (flag.equalsIgnoreCase("model")) {
                    cars = carRepository.findByModelNameContainingIgnoreCase(searchString, pageable).getContent();
                    count = carRepository.countByModelNameContainingIgnoreCase(searchString);
                } else if (flag.equalsIgnoreCase("color")) {
                    cars = carRepository.findByColorNameContainingIgnoreCase(searchString, pageable).getContent();
                    count = carRepository.countByColorNameContainingIgnoreCase(searchString);
                } else {
                    cars = carRepository.findByMakeNameContainingIgnoreCase(searchString, pageable).getContent();
                    count = carRepository.countByMakeNameContainingIgnoreCase(searchString);
                }
            } else {
                cars = carRepository.findByMakeNameContainingIgnoreCase(searchString, pageable).getContent();
                count = carRepository.countByMakeNameContainingIgnoreCase(searchString);
            }
        } else {
            cars = carRepository.findAll(pageable);
            count = carRepository.count();
        }
        CarGETResDto res = new CarGETResDto(cars, count, pageable);
        return res;
    }

    public CarDto findById(int id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            CarDto dto = new CarDto(car.get());
            return dto;
        }
        else 
            throw new RuntimeException("Car not found");
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

    private Car carMapper(Car car, CarPOSTDto carForm) {
        Model model = modelRepository.findById(carForm.getModelId())
                .orElseThrow(() -> new RuntimeException("Model not found"));
        Make make = makeRepository.findById(carForm.getMakeId())
                .orElseThrow(() -> new RuntimeException("Make not found"));
        Owner owner = ownerRepository.findById(carForm.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Color color = colorRepository.findById(carForm.getColorId())
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

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    public long count() {
        return carRepository.count();
    }
}
