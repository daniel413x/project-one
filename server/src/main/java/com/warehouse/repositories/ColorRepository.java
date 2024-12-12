package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Color;

import jakarta.transaction.Transactional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

      @Query("SELECT c FROM Color c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Color> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Color c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    long countByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE colors_id_seq RESTART WITH 1", nativeQuery = true)
    void resetIdSequence();
}
