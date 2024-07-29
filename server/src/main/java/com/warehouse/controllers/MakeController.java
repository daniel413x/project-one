package com.warehouse.controllers;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.dtos.MakeGETResDto;
import com.warehouse.dtos.MakePOSTDto;
import com.warehouse.models.Make;
import com.warehouse.services.MakeService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/makes")
public class MakeController {

    final private MakeService makeService;

    public MakeController(MakeService makeService) {
        this.makeService = makeService;
    }

    @GetMapping
    public MakeGETResDto findAll(@PageableDefault(size = 12) Pageable pageable, @RequestParam Optional<String> search) {
        return makeService.findAll(pageable, search);
    }


    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Make create(@Valid @RequestBody MakePOSTDto makeForm) {
        return makeService.save(makeForm);
    }
}
