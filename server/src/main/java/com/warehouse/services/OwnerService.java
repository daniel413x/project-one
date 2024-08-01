package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.owner.OwnerGETResDto;
import com.warehouse.models.Owner;
import com.warehouse.repositories.OwnerRepository;

@Service
public class OwnerService {

    final private OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public OwnerGETResDto findAll(Pageable pageable, @RequestParam Optional<String> search) {
        long count;
        Iterable<Owner> owners;
        if (search.isPresent()) {
            owners = ownerRepository.findAllByName(search.get(), pageable);
            count = ownerRepository.countByName(search.get());
        } else {
            owners = ownerRepository.findAll(pageable);
            count = ownerRepository.count();
        }
        OwnerGETResDto res = new OwnerGETResDto(owners, count, pageable);
        return res;
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
