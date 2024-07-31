package com.warehouse.controllers;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.dtos.MakeGETResDto;
import com.warehouse.dtos.MakePOSTDto;
import com.warehouse.dtos.MakePUTDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<Make> findById(@PathVariable int id) {
        Optional<Make> make = makeService.findById(id);
        if (make.isPresent())
            return ResponseEntity.ok(make.get());
        else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{name}/cars/count")
    public long getCarsCount(@PathVariable String name) {
        return makeService.getCarsCount(name);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Make create(@Valid @RequestBody MakePOSTDto makeForm) {
        return makeService.save(makeForm);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void putMethodName(@PathVariable int id, @Valid @RequestBody MakePUTDto makeForm) {
        makeService.update(id, makeForm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        makeService.deleteById(id);
    }
}
