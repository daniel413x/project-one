package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Make;

import jakarta.transaction.Transactional;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {
    
    Page<Make> findAllByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    long countAllByNameContainingIgnoreCase(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE makes_id_seq RESTART WITH 1", nativeQuery = true)
    void resetIdSequence();
}
