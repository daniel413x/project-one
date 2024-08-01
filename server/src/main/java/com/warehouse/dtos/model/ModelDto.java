package com.warehouse.dtos.model;

import com.warehouse.dtos.make.MakeDto;
import com.warehouse.models.Make;
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

    public ModelDto(int id, String name) {
      this.setId(id);
      this.setName(name);
    }

    public ModelDto(int id, String name, String makeName) {
      this.setId(id);
      this.setName(name);
      this.setName(makeName);
    }

    public ModelDto(int id, String name, Make make) {
      this.setId(id);
      this.setName(name);
      MakeDto makeDto = new MakeDto();
      makeDto.setId(make.getId());
      makeDto.setName(make.getName());
      this.make = makeDto;
    }

    private Integer id;

    private String name;

    private String makeName;

    private MakeDto make;
    
}
