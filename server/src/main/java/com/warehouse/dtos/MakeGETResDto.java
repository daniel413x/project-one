package com.warehouse.dtos;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.warehouse.models.Make;

public class MakeGETResDto {
  
  public MakeGETResDto(Iterable<Make> makes, long count, Pageable pageable) {
    rows = new ArrayList<>();
    makes.forEach(c -> {
      rows.add(new MakeDto(c));
    });
    this.count = count;
    this.pagination = new Pagination(pageable, count);
  }

  public List<MakeDto> rows;

  public long count;

  public Pagination pagination;
}

