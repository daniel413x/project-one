package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    
    // spring JPA dynamic query generation
    Page<Car> findByOwnerNameContainingIgnoreCase(String name, Pageable pageable);
    long countByOwnerNameContainingIgnoreCase(String name);
    
    Page<Car> findByModelNameContainingIgnoreCase(String name, Pageable pageable);
    long countByModelNameContainingIgnoreCase(String name);
    
    Page<Car> findByMakeNameContainingIgnoreCase(String name, Pageable pageable);
    long countByMakeNameContainingIgnoreCase(String name);
    
    Page<Car> findByColorNameContainingIgnoreCase(String name, Pageable pageable);
    long countByColorNameContainingIgnoreCase(String name);
}
