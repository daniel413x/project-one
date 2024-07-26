package com.warehouse.dtos;
import java.util.ArrayList;
import java.util.List;

import com.warehouse.models.Car;

public class CarGETResDto {
  
  public CarGETResDto(Iterable<Car> cars, long count) {
    rows = new ArrayList<>();
    cars.forEach(c -> {
      rows.add(new CarDto(c));
    });
    this.count = count;
  }

  public List<CarDto> rows;

  public long count;
}

