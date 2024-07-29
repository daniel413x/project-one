package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.ModelGETResDto;
import com.warehouse.dtos.ModelPOSTDto;
import com.warehouse.models.Model;
import com.warehouse.repositories.ModelRepository;

@Service
public class ModelService {

    final private ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public ModelGETResDto findAll(Pageable pageable, @RequestParam Optional<String> search) {
        long count;
        Iterable<Model> models;
        if (search.isPresent()) {
            models = modelRepository.findAllByName(search.get(), pageable);
            count = modelRepository.countByName(search.get());
        } else {
            models = modelRepository.findAll(pageable);
            count = modelRepository.count();
        }
        ModelGETResDto res = new ModelGETResDto(models, count, pageable);
        return res;
    }

    public Optional<Model> findById(int id) {
        return modelRepository.findById(id);
    }

    public Model save(ModelPOSTDto modelForm) {
        Model model = new Model();
        model.setName(modelForm.getName());
        return modelRepository.save(model);
    }

    public void update(int id, Model model) {
        if (!modelRepository.existsById(id)) 
            throw new NoSuchElementException("Model with id " + id + " does not exist");
        modelRepository.save(model);
    }

    public void deleteById(int id) {
        modelRepository.deleteById(id);
    }


}
