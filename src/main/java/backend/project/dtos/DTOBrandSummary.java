package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOBrandSummary {

    private Long brandId;
    private String brandName;
    private Integer countSales;
    private Double averageSalePrice;

}
