package com.warehouse.dtos.car;

import java.math.BigDecimal;
import java.util.Date;

import com.warehouse.models.Car;
import com.warehouse.models.Color;
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
      this.setColor(car.getColor());
      this.setYear(car.getYear());
      this.setPrice(car.getPrice());
      this.setMileage(car.getMileage());
      this.setRegistrationNumber(car.getRegistrationNumber());
      this.setInsurancePolicyNumber(car.getInsurancePolicyNumber());
      this.setInsuranceExpiration(car.getInsuranceExpiration());
      this.setRegistrationExpiration(car.getRegistrationExpiration());
      this.setLastMaintenanceDate(car.getLastMaintenanceDate());
    }

    private Integer id;

    private String vin;

    private Owner owner;

    private Make make;

    private Model model;

    private Color color;

    private Integer year;

    private BigDecimal price;

    private Integer mileage;

    private String registrationNumber;

    private String insurancePolicyNumber;

    private Date insuranceExpiration;

    private Date registrationExpiration;

    private Date lastMaintenanceDate;
}
