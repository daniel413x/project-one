package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Car;

@Repository
// notice CarRepository extends JpaRepository
// JpaRepository extends PagingAndSortingRepository
// PagingAndSortingRepository is the interface that offers pagination to your repositories
public interface CarRepository extends JpaRepository<Car, Integer> {
    
    @Query("SELECT c FROM Car c WHERE LOWER(c.owner.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Car> findAllByOwnerName(@Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Car c WHERE LOWER(c.owner.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    long countByOwnerName(@Param("name") String name);
}