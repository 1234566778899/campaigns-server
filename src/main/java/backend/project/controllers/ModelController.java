package backend.project.controllers;

import backend.project.dtos.DTOModel;
import backend.project.entities.Brand;
import backend.project.entities.Car;
import backend.project.entities.Model;
import backend.project.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//http://localhost:8080/api
@CrossOrigin("*")
public class ModelController {
    @Autowired
    ModelService modelService;

    @GetMapping("/models/brand/{brandId}")
    public ResponseEntity<List<DTOModel>> listAll(@PathVariable("brandId") Long brandId){
        //System.out.println("Entro "+brandId);
        return new ResponseEntity<>(modelService.listByBrandId(brandId), HttpStatus.OK);
        //return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<DTOModel> detailsById(@PathVariable("id") Long id){
        DTOModel modelFound = modelService.findById_DTO(id);
        if (modelFound!=null) {
            return new ResponseEntity<>(modelFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
