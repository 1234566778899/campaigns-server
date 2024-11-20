package backend.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DTOUser {
    private Long id;
    private String userName; //"gmorip"
    private String password;

    private boolean enabled;

    private String authorities; // "LlenarAsistencia;LlenarNotas"

}
