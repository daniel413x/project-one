package com.warehouse.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warehouse.models.Model;
import com.warehouse.repositories.ModelRepository;

@Service
public class ModelService {

    final private ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public Iterable<Model> findAll(Pageable pageable) {
        return modelRepository.findAll(pageable).getContent();
    }

    public Optional<Model> findById(int id) {
        return modelRepository.findById(id);
    }

    public Model save(Model model) {
        return modelRepository.save(model);
    }

    public void update(int id, Model model) {
        if (!modelRepository.existsById(id)) 
            throw new NoSuchElementException("Model with id " + id + " does not exist");
        // model.setId(id);
        modelRepository.save(model);
    }

    public void deleteById(int id) {
        modelRepository.deleteById(id);
    }


}
