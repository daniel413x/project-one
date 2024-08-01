package com.warehouse.services;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.ColorGETResDto;
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
}
