package com.warehouse.dtos.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerPUTDto extends OwnerPOSTDto {
    private int id;
    
}
