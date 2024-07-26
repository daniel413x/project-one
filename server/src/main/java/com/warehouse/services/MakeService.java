package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warehouse.models.Make;
import com.warehouse.repositories.MakeRepository;

@Service
public class MakeService {

    final private MakeRepository makeRepository;

    public MakeService(MakeRepository makeRepository) {
        this.makeRepository = makeRepository;
    }

    public Iterable<Make> findAll(Pageable pageable) {
        return makeRepository.findAll(pageable).getContent();
    }

    public Optional<Make> findById(int id) {
        return makeRepository.findById(id);
    }

    public Make save(Make make) {
        return makeRepository.save(make);
    }

    public void update(int id, Make make) {
        if (!makeRepository.existsById(id)) 
            throw new NoSuchElementException("Make with id " + id + " does not exist");
        makeRepository.save(make);
    }

    public void deleteById(int id) {
        makeRepository.deleteById(id);
    }


}
