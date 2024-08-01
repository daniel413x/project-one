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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.dtos.CarDto;
import com.warehouse.dtos.CarGETResDto;
import com.warehouse.dtos.CarPOSTDto;
import com.warehouse.dtos.CarPUTDto;
import com.warehouse.services.CarService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarController {

    final private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // this is the second place we implement pagination
    // we pass in the Pageable object in the method parameters
    // Pageable contains the page and size parameters we discussed earlier
    // we pass down the pageable object to the service
    @GetMapping
    public CarGETResDto findAll(@PageableDefault(size = 11) Pageable pageable, @RequestParam Optional<String> search) {
        return carService.findAll(pageable, search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    // save Car and return it as a dto
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public CarDto create(@Valid @RequestBody CarPOSTDto carForm) {
        return carService.save(carForm);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void putMethodName(@PathVariable int id, @Valid @RequestBody CarPUTDto carForm) {
        carService.update(id, carForm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        carService.deleteById(id);
    }
}
