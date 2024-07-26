package com.warehouse.dtos;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPOSTDto {
    private String vin;

    private int modelId;

    private int makeId;

    private int ownerId;

    private int colorId;

    private Integer year;
    
    private BigDecimal price;

    private Integer mileage;

    private String condition;

    private String registrationNumber;

    private String insurancePolicyNumber;

    private Date insuranceExpiration;

    private Date registrationExpiration;

    private Date lastMaintenanceDate;
}
