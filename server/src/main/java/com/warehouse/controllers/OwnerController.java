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

import com.warehouse.dtos.owner.OwnerDto;
import com.warehouse.dtos.owner.OwnerGETResDto;
import com.warehouse.dtos.owner.OwnerPOSTDto;
import com.warehouse.dtos.owner.OwnerPUTDto;
import com.warehouse.models.Owner;
import com.warehouse.services.OwnerService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public OwnerGETResDto findAll(@PageableDefault(size = 12) Pageable pageable, @RequestParam Optional<String> search) {
        return ownerService.findAll(pageable, search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findById(@PathVariable int id) {
        Optional<Owner> owner = ownerService.findById(id);
        if (owner.isPresent())
            return ResponseEntity.ok(owner.get());
        else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/cars/count")
    public long getCarsCount(@PathVariable int id) {
        return ownerService.getCarsCount(id);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public OwnerDto create(@Valid @RequestBody OwnerPOSTDto owner) {
        return ownerService.save(owner);
    }

    @PutMapping("/{id}")
    public void putMethodName(@PathVariable int id, @RequestBody OwnerPUTDto entity) {
        ownerService.save(id, entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        ownerService.deleteById(id);
    }
}
