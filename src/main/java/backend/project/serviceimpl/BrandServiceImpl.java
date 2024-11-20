package backend.project.serviceimpl;

import backend.project.dtos.DTOBrandSummary;
import backend.project.entities.Brand;
import backend.project.entities.Car;
import backend.project.entities.Model;
import backend.project.exceptions.InvalidActionException;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.BrandRepository;
import backend.project.repositories.ModelRepository;
import backend.project.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ModelRepository modelRepository;


    public Long isNameRegistered(String name) {
        if (brandRepository.findByName(name).isEmpty()) {
            return 0L;
        } else {
            return brandRepository.findByName(name).get(0).getId();
        }

        //return !brandRepository.findByName(name).isEmpty();
    }


    /*
    Quiero tener una lista donde se resuman las ventas de cada marca y el precio promedio pagado por cada marca

    idBrand, brandName, countSales, averageSalePrice
    1       Yamaha      3           31412
    2       Nissan      0           0
    3       Honda       2           53545
    ....
     */

    @Override
    public List<DTOBrandSummary> listBrandsSummary() {
        List<DTOBrandSummary> dtoBrandSummaries = new ArrayList<>();


        List<Brand> brands = listAll();
        for(Brand brand: brands){
            Long brandId = brand.getId();;
            String brandName =brand.getName();
            Integer countSale=0;
            Double sumSalePrice=0.0;
            Double averageSalePrice=0.0;


            for (Model model: brand.getModels()) {
                countSale = countSale + model.getCars().size();
                for (Car car: model.getCars()) {
                    sumSalePrice += car.getSalePrice();
                }
            }

/*
            countSale= brand.getModels().stream().map(m->m.getCars().size()).reduce(0,Integer::sum);
            sumSalePrice = brand.getModels().stream().map(m->m.getCars().stream().map(c->c.getSalePrice()).reduce(0.0,Double::sum)).reduce(0.0,Double::sum);
 */
            if (countSale>0) {
                averageSalePrice = sumSalePrice/countSale;
            }


            DTOBrandSummary dtoBrandSummary = new DTOBrandSummary(brandId, brandName, countSale, averageSalePrice);
            dtoBrandSummaries.add(dtoBrandSummary);
        }


        return dtoBrandSummaries;
    }

    @Override
    public Brand updateBrand(Brand brand) {
        Brand brandFound = findById(brand.getId());
        if (brandFound!=null){
            if (brand.getName()!=null) {
                if (brand.getName().isBlank()) {
                    throw new InvalidDataException("Brand name can not be blank");
                }
                Long idDuplicatedBrand = isNameRegistered(brand.getName());

                if (idDuplicatedBrand>0 && !idDuplicatedBrand.equals(brandFound.getId())) {
                    throw new KeyRepeatedDataException("Brand name: "+ brand.getName()+ " is already registered");
                }
                brandFound.setName(brand.getName());
            }
            if (brand.getCountry()!=null) {
                if (brand.getCountry().isBlank()) {
                    throw new InvalidDataException("Brand country can not be blank");
                }
                brandFound.setCountry(brand.getCountry());
            }

            if (brand.getFundationYear()!=null) {
                brandFound.setFundationYear(brand.getFundationYear());
            }

            brandFound.setActive(brand.isActive());

            return brandRepository.save(brandFound);

        } else {
            throw new ResourceNotFoundException("Brand with id: "+ brand.getId()+ " can not be found");
        }
    }

    @Override
    public Brand insertBrand(Brand brand) {

        if (brand.getName()==null || brand.getName().isBlank()) {
            throw new InvalidDataException("Brand name can not be blank");
        }
        if (isNameRegistered(brand.getName())!=0) {
            throw new KeyRepeatedDataException("Brand name: "+ brand.getName()+ " is already registered");
        }
        if (brand.getCountry()==null || brand.getCountry().isBlank()) {
            throw new InvalidDataException("Brand country can not be blank");
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand insertBrand(String name, String country, Integer fundationYear) {

       Brand brand = new Brand();
       brand.setName(name);
       brand.setCountry(country);
       brand.setFundationYear(fundationYear);

       return insertBrand(brand);
    }

    @Override
    public void deleteBrand(Long id, boolean forced) {
        Brand brandFound = brandRepository.findById(id).orElse(null);
        if (brandFound!=null) {
            if (forced) {
                for (Model m : brandFound.getModels()) {
                    m.setBrand(null);
                    modelRepository.delete(m);
                }
                brandFound.setModels(null);
                brandRepository.delete(brandFound);
            } else {
                if (brandFound.getModels().isEmpty()) {
                    brandRepository.delete(brandFound);
                }
            }
        }
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brandFound = findById(id);
        if (brandFound==null) {
            throw new ResourceNotFoundException("Brand with id: "+ id+ " can not be found");
        }
        if (!brandFound.getModels().isEmpty()) {
            throw new InvalidActionException("Brand with id: "+ id+ " can not be deleted because is used in FK");
        }
        brandRepository.delete(brandFound);
    }

    @Override
    public Brand findById(Long id) {
        Brand brandFound = brandRepository.findById(id).orElse(null);
        return brandFound;
    }

    @Override
    public List<Brand> listAll() {
        List<Brand> list=brandRepository.findAll();
        return list;
    }

    @Override
    public List<Brand> listByCountry(String country) {
        return brandRepository.findByCountry(country);
        /*
        List<Brand> list=brandRepository.findByCountry(country);
        for(Brand b:list) {
            b.setModels(null);
        }
        return list;
        */
    }

    @Override
    public List<Brand> listByCountryAndYear(String country, Integer year) {
        return brandRepository.findByCountryAndFundationYearGreaterThan(country,year);
    }

    @Override
    public Brand updateLogo(Long id, byte[] logo) {
        Brand brandFound = findById(id);
        if (brandFound!=null) {
            brandFound.setLogo(logo);
            return brandRepository.save(brandFound);
        } else {
            throw new ResourceNotFoundException("Brand with id: "+ id+ " can not be found");
        }

    }
}
