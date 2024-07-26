package com.warehouse.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 17)
    private String vin;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "make_id", nullable = false)
    private Make make;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(nullable = false)
    private Integer year;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Integer mileage;

    @Column(length = 50)
    private String condition;

    @Column(name = "registration_number", length = 50)
    private String registrationNumber;

    @Column(name = "insurance_policy_number", length = 50)
    private String insurancePolicyNumber;

    @Column(name = "insurance_expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insuranceExpiration;

    @Column(name = "registration_expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationExpiration;

    @Column(name = "last_maintenance_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMaintenanceDate;

    @ManyToMany
    @JoinTable(
        name = "CarStatusMapping",
        joinColumns = @JoinColumn(name = "car_id"),
        inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private List<CarStatus> statuses;
}
