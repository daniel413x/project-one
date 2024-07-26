package com.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warehouse.models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
