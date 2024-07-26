package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warehouse.models.Owner;
import com.warehouse.repositories.OwnerRepository;

@Service
public class OwnerService {

    final private OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Iterable<Owner> findAll(Pageable pageable) {
        return ownerRepository.findAll(pageable).getContent();
    }

    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    public void update(int id, Owner owner) {
        if (!ownerRepository.existsById(id)) 
            throw new NoSuchElementException("Owner with id " + id + " does not exist");
        owner.setId(id);
        ownerRepository.save(owner);
    }

    public void deleteById(int id) {
        ownerRepository.deleteById(id);
    }


}
