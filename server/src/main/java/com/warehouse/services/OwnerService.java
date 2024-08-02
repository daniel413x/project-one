package com.warehouse.services;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.owner.OwnerDto;
import com.warehouse.dtos.owner.OwnerGETResDto;
import com.warehouse.dtos.owner.OwnerPOSTDto;
import com.warehouse.dtos.owner.OwnerPUTDto;
import com.warehouse.models.Owner;
import com.warehouse.repositories.CarRepository;
import com.warehouse.repositories.OwnerRepository;

@Service
public class OwnerService {

    final private OwnerRepository ownerRepository;
    final private CarRepository carRepository;

    public OwnerService(OwnerRepository ownerRepository, CarRepository carRepository) {
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
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

    public long getCarsCount(int id) {
        return carRepository.countByOwnerId(id);
    }

    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    public OwnerDto save(OwnerPOSTDto ownerDto) {
        Owner owner = new Owner();
        owner = ownerMapper(owner, ownerDto);
        return new OwnerDto(ownerRepository.save(owner));
    }

    public void save(int id, OwnerPUTDto ownerDto) {
        Owner owner = new Owner();
        owner = ownerMapper(owner, ownerDto);
        owner.setId(id);
        ownerRepository.save(owner);
    }

    public Owner ownerMapper(Owner owner, OwnerPOSTDto dto) {
        owner.setName(dto.getName());
        owner.setContact(dto.getContact());
        return owner;
    }

    public void deleteById(int id) {
        carRepository.unassignOwnerFromCars(id);
        ownerRepository.deleteById(id);
    }
}
