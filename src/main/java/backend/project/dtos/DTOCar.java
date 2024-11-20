package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOCar {
    private Long id;
    private String license;
    private Integer fabricationYear;
    private Double salePrice;
    private LocalDate purchaseDate;
    private Long customerId;
    private Long modelId;
}
