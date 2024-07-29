package com.warehouse.dtos;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.warehouse.models.Owner;

public class OwnerGETResDto {
  
  public OwnerGETResDto(Iterable<Owner> owners, long count, Pageable pageable) {
    rows = new ArrayList<>();
    owners.forEach(c -> {
      rows.add(new OwnerDto(c));
    });
    this.count = count;
    this.pagination = new Pagination(pageable, count);
  }

  public List<OwnerDto> rows;

  public long count;

  public Pagination pagination;
}

