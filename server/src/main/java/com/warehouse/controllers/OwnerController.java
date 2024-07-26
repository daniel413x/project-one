package com.warehouse.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.models.Owner;
import com.warehouse.services.OwnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public Iterable<Owner> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return ownerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findById(@PathVariable int id) {
        Optional<Owner> owner = ownerService.findById(id);
        if (owner.isPresent())
            return ResponseEntity.ok(owner.get());
        else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Owner create(@Valid @RequestBody Owner owner) {
        logger.debug("====================================");
        logger.debug("POST request to /owners with Owner of " + owner);
        return ownerService.save(owner);
    }

    @PutMapping("/{id}")
    public void putMethodName(@PathVariable int id, @RequestBody Owner entity) {
        ownerService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        ownerService.deleteById(id);
    }
}
