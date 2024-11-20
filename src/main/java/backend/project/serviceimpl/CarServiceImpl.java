package backend.project.serviceimpl;

import backend.project.dtos.DTOCar;
import backend.project.dtos.DTOCarReport;
import backend.project.entities.Brand;
import backend.project.entities.Car;
import backend.project.entities.Customer;
import backend.project.entities.Model;
import backend.project.exceptions.InvalidActionException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.CarRespository;
import backend.project.services.CarService;
import backend.project.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRespository carRespository;


    @Autowired
    ModelService modelService;

    @Override
    public List<Car> listAll() {
        return carRespository.findAll();
    }

    @Override
    public List<DTOCarReport> listAllReport() {
        List<Car> cars = listAll();
        List<DTOCarReport> dtoCarReports = new ArrayList<>();
        for (Car car: cars){
            DTOCarReport dtoCarReport = new DTOCarReport(
                    car.getId(), car.getLicense(), car.getFabricationYear(), car.getSalePrice(),
                    car.getPurchaseDate(), car.getModel().getBrand().getName(), car.getModel().getName(),
                    null, null
            );
            if (car.getCustomer()!=null) {
                dtoCarReport.setCustomerFistName(car.getCustomer().getFirstName());
                dtoCarReport.setCustomerLastName(car.getCustomer().getLastName());
            }
            dtoCarReports.add(dtoCarReport);
        }
        return dtoCarReports;
    }

    @Override
    public Car addCar(DTOCar dtoCar) {
        Customer customer = null;
        Model model =  modelService.findById(dtoCar.getModelId());
        Car newCar = new Car(0L,dtoCar.getLicense(), dtoCar.getFabricationYear(), dtoCar.getSalePrice(), dtoCar.getPurchaseDate(),customer, model );
        newCar = carRespository.save(newCar);
        return newCar;
    }

    @Override
    public Car updateCar(DTOCar dtoCar) {
        DTOCar dtoCarFound = findByIdDTO(dtoCar.getId());
        if (dtoCarFound==null) {
            throw new ResourceNotFoundException("Car with id: "+ dtoCar.getId()+ " can not be found");
        } else {
            Model model =  modelService.findById(dtoCar.getModelId());
            Customer customer = null;


            Car updateCar = new Car(dtoCar.getId(), dtoCar.getLicense(), dtoCar.getFabricationYear(), dtoCar.getSalePrice(), dtoCar.getPurchaseDate(),customer, model );
            return carRespository.save(updateCar);
       }


    }

    @Override
    public DTOCar findByIdDTO(Long id) {
        Car carFound = carRespository.findById(id).orElse(null);
        if (carFound!=null){
            DTOCar dtoCar=new DTOCar(carFound.getId(), carFound.getLicense(), carFound.getFabricationYear(),
                    carFound.getSalePrice(), carFound.getPurchaseDate(),0L,carFound.getModel().getId());
            if (carFound.getCustomer()!=null){
                dtoCar.setCustomerId(carFound.getCustomer().getId());
            }
            return dtoCar;
        }
        return null;
    }

    @Override
    public void deleteCar(Long id) {
        Car carFound = carRespository.findById(id).orElse(null);
        if (carFound==null) {
            throw new ResourceNotFoundException("Car with id: "+ id+ " can not be found");
        }
        carRespository.delete(carFound);
    }
}
