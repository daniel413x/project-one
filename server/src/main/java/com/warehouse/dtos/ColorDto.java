package com.warehouse.dtos;

import com.warehouse.models.Color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {

    public ColorDto(Color color) {
      this.setId(color.getId());
      this.setName(color.getName());
    }

    private Integer id;

    private String name;
    
}
