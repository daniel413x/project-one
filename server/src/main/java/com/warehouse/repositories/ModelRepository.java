package com.warehouse.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Model;

import jakarta.transaction.Transactional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("SELECT m FROM Model m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Model> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT COUNT(m) FROM Model m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    long countByName(@Param("name") String name);

    @Query("SELECT m FROM Model m JOIN FETCH m.make WHERE m.id = :id")
    Optional<Model> findByIdWithMake(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE models_id_seq RESTART WITH 1", nativeQuery = true)
    void resetIdSequence();
}
