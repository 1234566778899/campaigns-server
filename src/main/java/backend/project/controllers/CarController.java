package backend.project.controllers;

import backend.project.dtos.DTOCar;
import backend.project.dtos.DTOCarReport;
import backend.project.entities.Brand;
import backend.project.entities.Car;
import backend.project.services.CarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//http://localhost:8080/api
@CrossOrigin("*")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> listAll(){
        return new ResponseEntity<>(carService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/cars/report")
    public ResponseEntity<List<DTOCarReport>> listAllReport(){
        return new ResponseEntity<>(carService.listAllReport(), HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<DTOCar> detailsById(@PathVariable("id") Long id){
        DTOCar carFound = carService.findByIdDTO(id);
        if (carFound!=null) {
            return new ResponseEntity<>(carFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") Long id, @RequestBody DTOCar dtoCar){

        dtoCar.setId(id);
        Car updatedCar = carService.updateCar(dtoCar);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);


    }

    @Transactional
    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody DTOCar dtoCar){
        Car newCar = carService.addCar(dtoCar);
        return new ResponseEntity<>(newCar, HttpStatus.OK);
    }


    @DeleteMapping("/cars/{id}")
    public ResponseEntity<HttpStatus> deleteCar( @PathVariable("id") Long id){
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
