package com.warehouse.dtos.car;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(pattern="MM/dd/yyyy", timezone="GMT-4")
    private Date insuranceExpiration;

    @JsonFormat(pattern="MM/dd/yyyy", timezone="GMT-4")
    private Date registrationExpiration;

    @JsonFormat(pattern="MM/dd/yyyy", timezone="GMT-4")
    private Date lastMaintenanceDate;
}
