package backend.project.services;

import backend.project.dtos.DTOCar;
import backend.project.dtos.DTOCarReport;
import backend.project.entities.Brand;
import backend.project.entities.Car;

import java.util.List;

public interface CarService {

    public List<Car> listAll();
    public List<DTOCarReport> listAllReport();
    public Car addCar(DTOCar car);
    public Car updateCar(DTOCar dtoCar);
    public DTOCar findByIdDTO(Long id);
    public void deleteCar(Long id);
}
