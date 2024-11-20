package backend.project.repositories;

import backend.project.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRespository extends JpaRepository<Car, Long> {

}
