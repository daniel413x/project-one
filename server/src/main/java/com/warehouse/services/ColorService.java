package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warehouse.models.Color;
import com.warehouse.repositories.ColorRepository;

@Service
public class ColorService {

    final private ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Iterable<Color> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable).getContent();
    }

    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    public Color save(Color color) {
        return colorRepository.save(color);
    }

    public void update(int id, Color color) {
        if (!colorRepository.existsById(id)) 
            throw new NoSuchElementException("Color with id " + id + " does not exist");
        colorRepository.save(color);
    }

    public void deleteById(int id) {
        colorRepository.deleteById(id);
    }


}
