package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.MakeGETResDto;
import com.warehouse.dtos.MakePOSTDto;
import com.warehouse.dtos.MakePUTDto;
import com.warehouse.models.Make;
import com.warehouse.repositories.CarRepository;
import com.warehouse.repositories.MakeRepository;

@Service
public class MakeService {

    final private MakeRepository makeRepository;

    final private CarRepository carRepository;

    public MakeService(MakeRepository makeRepository, CarRepository carRepository) {
        this.makeRepository = makeRepository;
        this.carRepository = carRepository;
    }

    public MakeGETResDto findAll(Pageable pageable, @RequestParam Optional<String> search) {
        long count;
        Iterable<Make> makes;
        if (search.isPresent()) {
            makes = makeRepository.findAllByNameContainingIgnoreCase(search.get(), pageable);
            count = makeRepository.countAllByNameContainingIgnoreCase(search.get());
        } else {
            makes = makeRepository.findAll(pageable);
            count = makeRepository.count();
        }
        MakeGETResDto res = new MakeGETResDto(makes, count, pageable);
        return res;
    }

    public Optional<Make> findById(int id) {
        return makeRepository.findById(id);
    }

    public long getCarsCount(String name) {
        return carRepository.countByMakeNameContainingIgnoreCase(name);
    }

    public Make save(MakePOSTDto makeForm) {
        Make make = new Make();
        make.setName(makeForm.getName());
        make.setLogoUrl(makeForm.getLogoUrl());
        return makeRepository.save(make);
    }

    public void update(int id, MakePUTDto makeForm) {
        if (!makeRepository.existsById(id)) 
            throw new NoSuchElementException("Make with id " + id + " does not exist");
        Make make = new Make();
        make.setName(makeForm.getName());
        make.setLogoUrl(makeForm.getLogoUrl());
        make.setId(id);
        makeRepository.save(make);
    }

    public void deleteById(int id) {
        makeRepository.deleteById(id);
    }
}
