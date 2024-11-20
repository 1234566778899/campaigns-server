package backend.project.services;

import backend.project.dtos.DTOBrandSummary;
import backend.project.entities.Brand;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface BrandService {

    public List<DTOBrandSummary> listBrandsSummary();
    public Brand updateBrand(Brand brand);
    public Brand insertBrand(Brand brand);
    public Brand insertBrand(String name, String country, Integer fundationYear);

    public void deleteBrand(Long id, boolean forced);

    public void deleteBrand(Long id);


    public Brand findById(Long id);
    public List<Brand> listAll();

    public List<Brand> listByCountry(String country);

    public List<Brand> listByCountryAndYear(String country, Integer year);

    public Brand updateLogo (Long id, byte[] logo);
}
