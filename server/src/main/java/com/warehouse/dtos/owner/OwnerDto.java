package com.warehouse.dtos.owner;

import com.warehouse.models.Owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {

    public OwnerDto(Owner owner) {
      this.setId(owner.getId());
      this.setName(owner.getName());
    }

    private Integer id;

    private String name;
    
}
