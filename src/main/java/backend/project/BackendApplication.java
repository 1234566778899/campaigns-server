package backend.project;

import backend.project.dtos.DTOUser;
import backend.project.entities.Authority;
import backend.project.entities.Brand;
import backend.project.entities.Car;
import backend.project.entities.Model;
import backend.project.repositories.BrandRepository;
import backend.project.repositories.CarRespository;
import backend.project.repositories.ModelRepository;
import backend.project.services.AuthorityService;
import backend.project.services.BrandService;
import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	public void ListarBrands(List<Brand> brandList){
		System.out.println("--------------------------------------");
		for(Brand brand : brandList){
			for (Model model: brand.getModels()) {
				model.setBrand(null);
			}
			System.out.println(brand);
		}
	}

	public void ListarModels(List<Model> modelList){
		System.out.println("--------------------------------------");
			for (Model model:modelList) {
				//Opcion 1
				//model.setBrand(null);
				//Opcion 2
				model.getBrand().setModels(null);
				System.out.println(model);
			}
	}


	@Bean
	public CommandLineRunner mappingDemo(
		BrandRepository brandRepository,
		ModelRepository modelRepository,
		CarRespository carRespository,
		BrandService brandService,
		UserService userService,
		AuthorityService authorityService
	) {
		return args -> {

			authorityService.addAuthority(new Authority(0L,"CONSULTA",null));
			authorityService.addAuthority(new Authority(0L,"REGISTRO",null));
			authorityService.addAuthority(new Authority(0L,"OTRO",null));


			userService.addUser(new DTOUser(0L,"gmorip","123",true,"REGISTRO;CONSULTA"));
			userService.addUser(new DTOUser(0L,"crevilla","321",true,"CONSULTA"));
			userService.addUser(new DTOUser(0L,"otro","123",true,"OTRO"));



			Brand brand1=new Brand(0L,"Toyota","Japon",1923,true,null, null);
			Brand brand2=new Brand(0L,"Nissan","Japon",1955,true,null, null);
			Brand brand3=new Brand(0L,"Yamaha","Japon",1978,true,null, null);
			Brand brand4=new Brand(0L,"Honda","Japon",1934,true,null, null);
			Brand brand5=new Brand(0L,"Audi","Alemania",1922,true,null, null);
			Brand brand6=new Brand(0L,"BMW","Alemania",1911,true,null, null);
			Brand brand7=new Brand(0L,"Mercedes Benz","Alemania",1942,true,null, null);

			/*
			//Usando Repository

			brand1=brandRepository.save(brand1);
			brandRepository.save(brand2);
			brandRepository.save(brand3);
			brandRepository.save(brand4);
			brandRepository.save(brand5);
			brandRepository.save(brand6);
			brandRepository.save(brand7);
 			*/

			//Usando Service

			brand1=brandService.insertBrand(brand1);
			brandService.insertBrand(brand2);
			brandService.insertBrand(brand3);
			brandService.insertBrand(brand4);
			brandService.insertBrand(brand5);
			brandService.insertBrand(brand6);
			brandService.insertBrand(brand7);

/*

			System.out.println(brand1);

			List<Brand> brands=brandRepository.findAll();
			ListarBrands(brands);
			System.out.println("--------------------------------------");
			Brand brandId6=brandRepository.findById(6L).get();
			System.out.println(brandId6);

			List<Brand> brandsDeUnPaisSQL=brandRepository.buscarXPaisSQL("Japon");
			ListarBrands(brandsDeUnPaisSQL);
			List<Brand> brandsDeUnPaisJPQL=brandRepository.buscarXPaisJPQL("Japon");
			ListarBrands(brandsDeUnPaisJPQL);
			List<Brand> brandsDeUnPaisQueryMethod=brandRepository.findByCountryOrderByNameDesc("Japon");
			ListarBrands(brandsDeUnPaisQueryMethod);

			List<Brand> brandsDeUnPaisQueryMethod2=brandRepository.findByCountryAndFundationYearGreaterThan("Japon",1950);
			ListarBrands(brandsDeUnPaisQueryMethod2);

			System.out.println("--------------------------------------");


 */
			/*
			//Este tipo de update reemplaza con null todos los valores que no fueron definidos ("chanca" el registro)

			Brand brandACambiar=new Brand();
			brandACambiar.setId(3L);
			brandACambiar.setName("Lexus");
			brandRepository.save(brandACambiar);
			List<Brand> brands2=brandRepository.findAll();
			ListarBrands(brands2);
			*/

			/*
			Brand brandACambiar = brandRepository.findById(3L).get();
			brandACambiar.setName("Lexus");
			brandRepository.save(brandACambiar);
			List<Brand> brands2=brandRepository.findAll();
			ListarBrands(brands2);
*/
			Brand brandXModel1=brandRepository.findById(1L).get();
			Model model1=new Model(0L,"Yaris",brandXModel1,null);
			Model model2=new Model(0L,"Corolla",brandXModel1,null);
			Model model3=new Model(0L,"Hilux",brandXModel1,null);

			Brand brandXModel2=brandRepository.findById(2L).get();
			Model model4=new Model(0L,"Sentra",brandXModel2,null);
			Model model5=new Model(0L,"Emporio",brandXModel2,null);
			Model model6=new Model(0L,"Runner",brandXModel2,null);

			model1=modelRepository.save(model1);
			model2=modelRepository.save(model2);
			model3=modelRepository.save(model3);
			model4=modelRepository.save(model4);
			model5=modelRepository.save(model5);
			model6=modelRepository.save(model6);


			Car car1=new Car(0L,"ASX100",2023,41342.0, LocalDate.now(),null,model1);
			Car car2=new Car(0L,"ASX101",2023,23124.0,null,null,model1);
			Car car3=new Car(0L,"ASX102",2023,12312.0,null,null,model1);
			Car car4=new Car(0L,"ASX103",2023,12341.0,null,null,model2);
			Car car5=new Car(0L,"ASX104",2023,34232.0,null,null,model2);
			Car car6=new Car(0L,"ASX105",2023,34222.0,null,null,model2);
			Car car7=new Car(0L,"ASX106",2023,24212.0,null,null,model3);
			Car car8=new Car(0L,"ASX107",2023,42124.0,null,null,model3);
			Car car9=new Car(0L,"ASX108",2023,53114.0,null,null,model3);
			Car car10=new Car(0L,"ASX109",2023,12343.0,null,null,model4);
			Car car11=new Car(0L,"ASX110",2023,54313.0,null,null,model4);
			Car car12=new Car(0L,"ASX111",2023,43523.0,null,null,model4);
			Car car13=new Car(0L,"ASX112",2023,53311.0,null,null,model5);
			Car car14=new Car(0L,"ASX113",2023,54311.0,null,null,model5);
			Car car15=new Car(0L,"ASX114",2023,24323.0,null,null,model6);
			Car car16=new Car(0L,"ASX115",2023,13422.0,null,null,model6);


			carRespository.save(car1);
			carRespository.save(car2);
			carRespository.save(car3);
			carRespository.save(car4);
			carRespository.save(car5);
			carRespository.save(car6);
			carRespository.save(car7);
			carRespository.save(car8);
			carRespository.save(car9);
			carRespository.save(car10);
			carRespository.save(car11);
			carRespository.save(car12);
			carRespository.save(car13);
			carRespository.save(car14);
			carRespository.save(car15);
			carRespository.save(car16);



			/*

			List<Brand> brands3=brandRepository.findAll();
			ListarBrands(brands3);

			List<Model> modelsXBrand=modelRepository.findByBrand_Id(2L);
			ListarModels(modelsXBrand);

			//Mostrar los modelos de la marca que un usuario escriba
			System.out.println("--------------------------");
			Scanner scanner=new Scanner(System.in);
			System.out.print("Escriba el nombre de la marca: ");
			String nombreMarca = scanner.nextLine();

			if (brandRepository.findByName(nombreMarca).size()>0) {
				Brand foundBrand = brandRepository.findByName(nombreMarca).get(0);
				System.out.print("\nLos modelos disponibles de esa marca son:\n");
				for (Model model : foundBrand.getModels()) {
					System.out.println(model.getName());
				}
			} else {
				System.out.print("\nNo se encuentra la marca ingresada\n");
			}
			scanner.close();

			//--------------------------
			brandService.deleteBrand(5L,true);
			brandService.deleteBrand(1L,true);

			List<Brand> brandPostDelete=brandRepository.findAll();
			ListarBrands(brandPostDelete);



			 */
		};
	}

}
