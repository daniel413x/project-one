package com.warehouse.dtos;

import com.warehouse.models.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDto {

    public ModelDto(Model model) {
      this.setId(model.getId());
      this.setName(model.getName());
      this.setMakeName(model.getMake().getName());
    }

    private Integer id;

    private String name;

    private String makeName;
    
}
