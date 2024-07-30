package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Make;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {
    
    Page<Make> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    long countAllByNameContainingIgnoreCase(@Param("name") String name);
}
