package com.warehouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Make;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {

    @Query("SELECT m FROM Make m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Make> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(m) FROM Make m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    long countByName(@Param("name") String name);
}
