package com.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
}
