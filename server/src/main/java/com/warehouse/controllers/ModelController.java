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

import com.warehouse.dtos.model.ModelGETResDto;
import com.warehouse.dtos.model.ModelPOSTDto;
import com.warehouse.dtos.model.ModelPUTDto;
import com.warehouse.models.Model;
import com.warehouse.services.MakeService;
import com.warehouse.services.ModelService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/models")
public class ModelController {

    final private ModelService modelService;

    public ModelController(ModelService modelService, MakeService makeService) {
        this.modelService = modelService;
    }

    @GetMapping
    public ModelGETResDto findAll(@PageableDefault(size = 12) Pageable pageable, @RequestParam Optional<String> search) {
        return modelService.findAll(pageable, search);
    }

    @GetMapping("/{name}/cars/count")
    public long getCarsCount(@PathVariable String name) {
        return modelService.getCarsCount(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> findById(@PathVariable int id) {
        return ResponseEntity.ok(modelService.findById(id));
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Model create(@Valid @RequestBody ModelPOSTDto modelForm) {
        return modelService.save(modelForm);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ModelPUTDto modelForm, @PathVariable int id) {
        modelService.save(id, modelForm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        modelService.deleteById(id);
    }
}
