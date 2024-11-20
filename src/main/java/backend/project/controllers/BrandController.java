package backend.project.controllers;

import backend.project.dtos.DTOBrandSummary;
import backend.project.entities.Brand;
import backend.project.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
//http://localhost:8080/api
@CrossOrigin("*")
public class BrandController {

    @Autowired
    BrandService brandService;


    //http://localhost:8080/api/brands
    //Cuando llaman a esta ruta se tiene de devolver la lista completa de todas las marcas (Brand)
    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> listAllBrands(){
        return new ResponseEntity<>(brandService.listAll(), HttpStatus.OK);
    }


    @PostMapping("/brands")
    public ResponseEntity<Brand> addBrand(@RequestBody Brand brand){
        Brand newBrand=brandService.insertBrand(brand);
        if (newBrand==null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }


    @PutMapping("/brands/{id}/logo")
    public ResponseEntity<Brand> updateLogo(@PathVariable("id") Long id,
                                            @RequestPart("logo") MultipartFile logo) throws IOException {
        Brand updatedBrand = brandService.updateLogo(id, logo.getBytes());
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<HttpStatus> deleteBrand( @PathVariable("id") Long id){
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable("id") Long id, @RequestBody Brand brand){
        brand.setId(id);
        Brand updatedBrand = brandService.updateBrand(brand);
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @PutMapping("/brands")
    public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand){
        Brand updatedBrand = brandService.updateBrand(brand);
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }



    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> detailsById(@PathVariable("id") Long id){
        Brand brandFound = brandService.findById(id);
        if (brandFound!=null) {
            return new ResponseEntity<>(brandFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/brands/country/{country}")
    public ResponseEntity<List<Brand>> listByCountry(@PathVariable("country") String country){
        return new ResponseEntity<>(brandService.listByCountry(country), HttpStatus.OK);
    }


    @GetMapping("/brands/country/{country}/year/{year}")
    public ResponseEntity<List<Brand>> listByCountryAndYear(@PathVariable("country") String country,
                                                     @PathVariable("year") Integer year){
        return new ResponseEntity<>(brandService.listByCountryAndYear(country, year), HttpStatus.OK);
    }


    @GetMapping("/brands/summary")
    public ResponseEntity<List<DTOBrandSummary>> listBrandsSummary(){
        return new ResponseEntity<>( brandService.listBrandsSummary() , HttpStatus.OK);
    }


}
