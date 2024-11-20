package backend.project.repositories;

import backend.project.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    //Query en SQL Nativo
    @Query(nativeQuery = true, value = "SELECT * FROM brands WHERE country=?1")
    public List<Brand> buscarXPaisSQL(String country);

    //Query en JPQL
    @Query(nativeQuery = false, value = "SELECT b FROM Brand b WHERE b.country=?1")
    public List<Brand> buscarXPaisJPQL(String country);

    //Query Method
    public List<Brand> findByCountry(String country);

    public List<Brand> findByName(String name);

    public List<Brand> findByCountryOrderByNameDesc(String country);

    public List<Brand> findByCountryAndFundationYearGreaterThan(String country, Integer fundationYear);


}
