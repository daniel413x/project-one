package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Car;

import jakarta.transaction.Transactional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    
    // spring JPA dynamic query generation
    long countByOwnerId(int id);
    
    Page<Car> findByOwnerNameContainingIgnoreCase(String name, Pageable pageable);
    long countByOwnerNameContainingIgnoreCase(String name);
    
    Page<Car> findByModelNameContainingIgnoreCase(String name, Pageable pageable);
    long countByModelNameContainingIgnoreCase(String name);
    
    Page<Car> findByMakeNameContainingIgnoreCase(String name, Pageable pageable);
    long countByMakeNameContainingIgnoreCase(String name);
    
    Page<Car> findByColorNameContainingIgnoreCase(String name, Pageable pageable);
    long countByColorNameContainingIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Car c SET c.owner = null WHERE c.owner.id = :ownerId")
    void unassignOwnerFromCars(@Param("ownerId") int ownerId);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE cars_id_seq RESTART WITH 1", nativeQuery = true)
    void resetIdSequence();
}
