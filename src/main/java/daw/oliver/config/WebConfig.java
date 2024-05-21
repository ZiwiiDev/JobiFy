// Declaro el paquete al que pertenece esta clase
package daw.oliver.config;
/*-------------------------------------------------------------*/
// Importo las clases y anotaciones necesarias de "Spring"
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*-------------------------------------------------------------*/
// Anoto la clase con "@Configuration" para indicar que contiene configuraciones de "Spring"
@Configuration
public class WebConfig implements WebMvcConfigurer {
	/*-------------------------------------------------------------*/
	/* VARIABLES */
	
    // Inyecto el valor de 'jobify.ruta.imagenes' desde "application.properties" usando "@Value"
    @Value("${jobify.ruta.imagenes}")
    private String rutaImagenes;
    
    // Inyecto el valor de 'jobify.ruta.cv' desde "application.properties" usando "@Value"
    @Value("${jobify.ruta.cv}")
    private String rutaCV;
    /*-------------------------------------------------------------*/
    /* MÉTODOS */
    
    // Defino el método para agregar manejadores de recursos estáticos
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// Agrego un manejador de recursos para "/logos/**" y especifico la ubicación en el sistema de archivos usando la ruta desde "application.properties"
    	// Importante poner "file" antes de la ruta en "Windows".
    	// 'logos' no existe físicamente, se crea virtualmente en la aplicación.
        registry.addResourceHandler("/logos/**").addResourceLocations("file:" + rutaImagenes); // Windows
        
        // Agrego un manejador de recursos para "/cv/**" y especifico la ubicación en el sistema de archivos usando la ruta desde "application.properties"
        registry.addResourceHandler("/cv/**").addResourceLocations("file:" + rutaCV); // Windows
    }//end addResourceHandlers(ResourceHandlerRegistry registry)
    /*-------------------------------------------------------------*/
}//end class WebConfig
/*-------------------------------------------------------------*/
