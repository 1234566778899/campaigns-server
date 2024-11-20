package backend.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ErrorMessage {

    private Integer status;
    private String exception;
    private String message;
    private String uri;  //uri
    private LocalDateTime timestamp;

    /*
    URI: Universal Resource Identificator
        URL: Universal Resource Locator       ("https://intranet.upc.edu.pe/introduccion.asp")
        URN: Univsrsal Resource Name          (urn:isbn:743742737)
     */


}
