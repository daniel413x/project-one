package com.warehouse.dtos;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.warehouse.models.Car;

public class CarGETResDto {
  
  public CarGETResDto(Iterable<Car> cars, long count, Pageable pageable) {
    rows = new ArrayList<>();
    cars.forEach(c -> {
      rows.add(new CarDto(c));
    });
    this.count = count;
    this.pagination = new Pagination(pageable, count);
  }

  public List<CarDto> rows;

  public long count;

  public Pagination pagination;
}

