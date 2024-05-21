// Declaro el paquete al que pertenece esta clase
package daw.oliver;
/*-------------------------------------------------------------*/
// Importo la clase "SpringApplication" del paquete "org.springframework.boot"
import org.springframework.boot.SpringApplication;
// Importo la anotación "SpringBootApplication" del paquete "org.springframework.boot.autoconfigure"
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*-------------------------------------------------------------*/
// Anoto la clase con "@SpringBootApplication" para indicar que es una aplicación "Spring Boot"
@SpringBootApplication
public class EmpleosApplication {
	/*-------------------------------------------------------------*/
    // Defino el método principal que es el punto de entrada de la aplicación
    public static void main(String[] args) {
        // Ejecuto la aplicación Spring Boot
        SpringApplication.run(EmpleosApplication.class, args);
    }//end main
    /*-------------------------------------------------------------*/
}//end class EmpleosApplication
/*-------------------------------------------------------------*/
