package com.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Car;

@Repository
// notice CarRepository extends JpaRepository
// JpaRepository extends PagingAndSortingRepository
// PagingAndSortingRepository is the interface that offers pagination to your repositories
public interface CarRepository extends JpaRepository<Car, Integer> {
}