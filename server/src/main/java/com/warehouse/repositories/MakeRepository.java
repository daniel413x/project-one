package com.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Make;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {
}
