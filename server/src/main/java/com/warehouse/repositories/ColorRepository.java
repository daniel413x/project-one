package com.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
}
