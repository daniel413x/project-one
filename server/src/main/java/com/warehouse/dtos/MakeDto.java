package com.warehouse.dtos;

import java.util.List;
import java.util.stream.Collectors;

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
      this.setLogoUrl(make.getLogoUrl());
      this.setModels(make.getModels().stream().map(ModelDto::new).collect(Collectors.toList()));
    }

    public MakeDto(int id, String name) {
      this.setId(id);
      this.setName(name);
    }

    private Integer id;

    private String name;
    
    private String logoUrl;

    public List<ModelDto> models;

}
