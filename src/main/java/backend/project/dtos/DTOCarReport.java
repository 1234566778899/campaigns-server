package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOCarReport {
    private Long id;
    private String license;
    private Integer fabricationYear;
    private Double salePrice;
    private LocalDate purchaseDate;
    private String brandName;
    private String modelName;
    private String customerFistName;
    private String customerLastName;

}
