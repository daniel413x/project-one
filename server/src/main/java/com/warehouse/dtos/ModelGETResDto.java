package com.warehouse.dtos;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.warehouse.models.Model;

public class ModelGETResDto {
  
  public ModelGETResDto(Iterable<Model> models, long count, Pageable pageable) {
    rows = new ArrayList<>();
    models.forEach(c -> {
      rows.add(new ModelDto(c));
    });
    this.count = count;
    this.pagination = new Pagination(pageable, count);
  }

  public List<ModelDto> rows;

  public long count;

  public Pagination pagination;
}

