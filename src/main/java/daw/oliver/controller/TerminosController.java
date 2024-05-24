// Declaro el paquete al que pertenece esta clase
package daw.oliver.controller;
/*-------------------------------------------------------------*/
// Importo las clases y anotaciones necesarias de "Spring"
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TerminosController {
	/*-------------------------------------------------------------*/
	// Defino el método para mostrar la página de términos y condiciones
    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }//end terms()
    /*-------------------------------------------------------------*/
}//end class TerminosController
/*-------------------------------------------------------------*/
