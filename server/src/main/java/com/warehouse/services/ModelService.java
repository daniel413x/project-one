package com.warehouse.services;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.warehouse.dtos.model.ModelGETResDto;
import com.warehouse.dtos.model.ModelPOSTDto;
import com.warehouse.dtos.model.ModelPUTDto;
import com.warehouse.models.Make;
import com.warehouse.models.Model;
import com.warehouse.repositories.CarRepository;
import com.warehouse.repositories.MakeRepository;
import com.warehouse.repositories.ModelRepository;

@Service
public class ModelService {

    final private ModelRepository modelRepository;

    final private CarRepository carRepository;


    final private MakeRepository makeRepository;

    public ModelService(ModelRepository modelRepository, MakeRepository makeRepository, CarRepository carRepository) {
        this.modelRepository = modelRepository;
        this.carRepository = carRepository;
        this.makeRepository = makeRepository;
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

    public Model findById(int id) {
        Optional<Model> model = modelRepository.findByIdWithMake(id);
        if (model.isPresent()) {
            return model.get();
        }
        else 
            throw new RuntimeException("Model not found");
    }


    public long getCarsCount(String name) {
        return carRepository.countByModelNameContainingIgnoreCase(name);
    }

    public Model save(ModelPOSTDto modelForm) {
        Model model = new Model();
        model = modelMapper(model, modelForm);
        return modelRepository.save(model);
    }

    public void save(int id, ModelPUTDto carForm) {
        Model model = findById(id);
        Model updatedModel = modelMapper(model, carForm);
        modelRepository.save(updatedModel);
    }

    private Model modelMapper(Model model, ModelPOSTDto modelForm) {
        Make make = makeRepository.findById(modelForm.getMakeId())
            .orElseThrow(() -> new RuntimeException("Make not found"));
        model.setMake(make);
        model.setName(modelForm.getName());
        return model;
    }

    public void deleteById(int id) {
        modelRepository.deleteById(id);
    }


}
