package com.warehouse.dtos;

import com.warehouse.models.Car;
import com.warehouse.models.Make;
import com.warehouse.models.Model;
import com.warehouse.models.Owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    public CarDto(Car car) {
      this.setId(car.getId());
      this.setVin(car.getVin());
      this.setOwner(car.getOwner());
      this.setOwner(car.getOwner());
      this.setMake(car.getMake());
      this.setModel(car.getModel());
    }

    private Integer id;

    private String vin;

    private Owner owner;

    private Make make;

    private Model model;
}
