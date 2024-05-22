// Declaro el paquete al que pertenece esta clase
package daw.oliver.util;
/*-------------------------------------------------------------*/
//Importo las clases y anotaciones necesarias
import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
/*-------------------------------------------------------------*/
public class Utilidades {
	/*-------------------------------------------------------------*/
	// Método para guardar un archivo imagen en el disco duro del equipo
	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		// Obtengo el nombre original del archivo
		String nombreOriginal = multiPart.getOriginalFilename();
		
		// Cambio los espacios por guiones
		nombreOriginal = nombreOriginal.replace(" ", "-");
		
		// Cadena aleatoria de 8 caracteres para mayor SEGURIDAD
		String nombreFinal = generarAlfanumericoRandom(8)+ nombreOriginal;
		
		// Formo el nombre del archivo para guardarlo en el disco duro
		try {
			File imageFile = new File(ruta + nombreFinal);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			
			// Guardo físicamente el archivo en HD
			multiPart.transferTo(imageFile);
			
			// Devuelvo el nombre final
			return nombreFinal;
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			return null;
		}//end catch
	}//end guardarArchivo(MultipartFile multiPart, String ruta)
	
	// Método que genera caracteres alfanuméricos aleatorios para dar mayor SEGURIDAD
	public static String generarAlfanumericoRandom(int contador) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		
		// Mientras no sea 0
		while (contador-- != 0) {
			// Creo caracteres aleatorios referidos a una posición del alfabeto
			int caracter = (int) (Math.random() * CARACTERES.length());
			
			// Concateno el caracter
			builder.append(CARACTERES.charAt(caracter));
		}//end while
		
		// Devuelvo la cadena entera
		return builder.toString();
	}//end generarAlfanumericoRandom(int contador)
	/*-------------------------------------------------------------*/
}//end class Utilidades
/*-------------------------------------------------------------*/
