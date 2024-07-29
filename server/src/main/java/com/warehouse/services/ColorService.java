package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.ColorGETResDto;
import com.warehouse.dtos.ColorPOSTDto;
import com.warehouse.models.Color;
import com.warehouse.repositories.ColorRepository;

@Service
public class ColorService {

    final private ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public ColorGETResDto findAll(Pageable pageable, @RequestParam Optional<String> search) {
        long count;
        Iterable<Color> colors;
        if (search.isPresent()) {
            colors = colorRepository.findAllByName(search.get(), pageable);
            count = colorRepository.countByName(search.get());
        } else {
            colors = colorRepository.findAll(pageable);
            count = colorRepository.count();
        }
        ColorGETResDto res = new ColorGETResDto(colors, count, pageable);
        return res;
    }

    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    public Color save(ColorPOSTDto colorForm) {
        Color color = new Color();
        color.setName(colorForm.getName());
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
