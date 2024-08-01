package com.warehouse.dtos.color;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.warehouse.dtos.Pagination;
import com.warehouse.models.Color;

public class ColorGETResDto {
  
  public ColorGETResDto(Iterable<Color> colors, long count, Pageable pageable) {
    rows = new ArrayList<>();
    colors.forEach(c -> {
      rows.add(new ColorDto(c));
    });
    this.count = count;
    this.pagination = new Pagination(pageable, count);
  }

  public List<ColorDto> rows;

  public long count;

  public Pagination pagination;
}

