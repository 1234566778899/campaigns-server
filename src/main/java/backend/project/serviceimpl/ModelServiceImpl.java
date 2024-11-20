package backend.project.serviceimpl;


import backend.project.dtos.DTOModel;
import backend.project.entities.Model;
import backend.project.repositories.ModelRepository;
import backend.project.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelRepository modelRepository;

    @Override
    public Model findById(Long id) {
        Model modelFound = modelRepository.findById(id).orElse(null);
        return modelFound;
    }

    @Override
    public DTOModel findById_DTO(Long id) {
        Model modelFound = findById(id);
        if (modelFound!=null) {
            return new DTOModel(modelFound.getId(), modelFound.getName(), modelFound.getBrand().getId());
        }
        return null;
    }

    @Override
    public List<DTOModel> listByBrandId(Long brandId) {

        List<Model> models=modelRepository.findByBrand_Id(brandId);

        List<DTOModel> dtoModels = new ArrayList<>();
        for (Model model: models){
            dtoModels.add(new DTOModel(model.getId(), model.getName(), model.getBrand().getId()));
        }

        return  dtoModels;
    }
}
