// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
/*-------------------------------------------------------------*/
// Importo las clases y anotaciones necesarias de "Spring"
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
/*-------------------------------------------------------------*/
@Controller
public class CustomErrorController implements ErrorController {
    /*-------------------------------------------------------------*/
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Obtén el código de estado de error
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        
        // Verifico el tipo de error
        if (statusCode != null) {
        	
        	// Cada tipo de error
            switch (statusCode) {
                case 404:
                    return "error-404";
                case 500:
                    return "error-500";
                // Añadir otros códigos de estado (errores)
                default:
                    return "error";
            }//end switch
        }//end if
        return "error";
    }//end handleError(HttpServletRequest request)

    public String getErrorPath() {
        return "/error";
    }//end getErrorPath
    /*-------------------------------------------------------------*/
}//end class CustomErrorController
/*-------------------------------------------------------------*/
