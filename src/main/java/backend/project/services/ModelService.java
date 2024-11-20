package backend.project.services;

import backend.project.dtos.DTOModel;
import backend.project.entities.Model;

import java.util.List;

public interface ModelService {

    public Model findById(Long id);
    public DTOModel findById_DTO(Long id);


    public List<DTOModel> listByBrandId(Long brandId);

}
