package backend.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private Integer fundationYear;
    private boolean active;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logo;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    private List<Model> models;

}
