package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String license;
    private Integer fabricationYear;
    private Double salePrice;
    private LocalDate purchaseDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;

}
