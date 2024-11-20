package backend.project.repositories;

import backend.project.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model,Long> {

    public List<Model> findByBrand_Id(Long brand_id);


}
