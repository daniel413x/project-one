package com.warehouse.dtos;

import com.warehouse.models.Make;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeDto {

    public MakeDto(Make make) {
      this.setId(make.getId());
      this.setName(make.getName());
    }

    private Integer id;

    private String name;
    
}
