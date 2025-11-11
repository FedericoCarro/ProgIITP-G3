package prog2.progiitp.g3.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarExcepcionesGenericas(RuntimeException ex) {
        ex.printStackTrace();
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400 
        String mensaje = ex.getMessage();

        if (mensaje.contains("no encontrado") || mensaje.contains("no existe")) {
            status = HttpStatus.NOT_FOUND; // 404
        } else if (mensaje.contains("Acceso denegado")) {
             status = HttpStatus.FORBIDDEN; // 403
        } else if (mensaje.contains("ya existe") || mensaje.contains("no es válido")) {
             status = HttpStatus.BAD_REQUEST; // 400
        }
        
        // Si no es un error conocido, es mejor un 500
        if (status == HttpStatus.BAD_REQUEST && !mensaje.contains("ya existe") && !mensaje.contains("no es válido")) {
             status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
             mensaje = "Ocurrió un error interno en el servidor.";
        }

        return new ResponseEntity<>(mensaje, status);
    }
   }