package com.warehouse.controllers;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.dtos.CarGETResDto;
import com.warehouse.dtos.CarPOSTDto;
import com.warehouse.models.Car;
import com.warehouse.models.Color;
import com.warehouse.models.Make;
import com.warehouse.models.Model;
import com.warehouse.models.Owner;
import com.warehouse.services.CarService;
import com.warehouse.services.ColorService;
import com.warehouse.services.MakeService;
import com.warehouse.services.ModelService;
import com.warehouse.services.OwnerService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarController {

    final private CarService carService;

    final private MakeService makeService;

    final private ModelService modelService;

    final private OwnerService ownerService;

    final private ColorService colorService;

    public CarController(CarService carService, MakeService makeService, ModelService modelService, OwnerService ownerService, ColorService colorService) {
        this.carService = carService;
        this.makeService = makeService;
        this.modelService = modelService;
        this.ownerService = ownerService;
        this.colorService = colorService;
    }

    // must use string quotes for the paginated requests to work
    // curl "http://localhost:8080/cars?page=0&size=2"

    // this is the second place we implement pagination
    // we pass in the Pageable object in the method parameters
    // Pageable contains the page and size parameters we discussed earlier
    // we pass down the pageable object to the service
    @GetMapping
    public CarGETResDto findAll(@PageableDefault(size = 12) Pageable pageable) {
        Iterable<Car> cars = carService.findAll(pageable);
        long count = carService.count();
        System.out.println(count);
        CarGETResDto res = new CarGETResDto(cars, count);
        return res;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable int id) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent())
            return ResponseEntity.ok(car.get());
        else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping() // POST http://localhost:8080/cars
    @ResponseStatus(code = HttpStatus.CREATED)
    public Car create(@Valid @RequestBody CarPOSTDto carForm) {
        Car car = new Car();
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
        car.setCondition(carForm.getCondition());
        car.setInsuranceExpiration(carForm.getInsuranceExpiration());
        car.setInsurancePolicyNumber(carForm.getInsurancePolicyNumber());
        car.setLastMaintenanceDate(carForm.getLastMaintenanceDate());
        car.setMileage(carForm.getMileage());
        car.setPrice(carForm.getPrice());
        car.setVin(carForm.getVin());
        car.setYear(carForm.getYear());
        return carService.save(car);
    }

    @PutMapping("/{id}")
    public void putMethodName(@PathVariable int id, @RequestBody Car entity) {
        carService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        carService.deleteById(id);
    }
}
