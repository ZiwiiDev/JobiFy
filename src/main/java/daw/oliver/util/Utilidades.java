package daw.oliver.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utilidades {
	
	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		// Obtengo el nombre original del archivo
		String nombreOriginal = multiPart.getOriginalFilename();
		
		// Cambiar espacios por guiones
		nombreOriginal = nombreOriginal.replace(" ", "-");
		
		// Cadena aleatoria de 8 caracteres
		String nombreFinal = generarAlfanumericoRandom(8)+ nombreOriginal;
		
		try {
		// Formo el nombre del archivo para guardarlo en el disco duro
		File imageFile = new File(ruta + nombreFinal);
		System.out.println("Archivo: " + imageFile.getAbsolutePath());
		
		// Guardo físicamente el archivo en HD
		multiPart.transferTo(imageFile);
		
		// Devuelvo el nombre final
		return nombreFinal;
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			return null;
		}
	}
	
	public static String generarAlfanumericoRandom(int contador) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		
		// Mientras no sea 0
		while (contador-- != 0) {
			// Creo caracteres aleatorios referidos a una posición del alfabeto
			int caracter = (int) (Math.random() * CARACTERES.length());
			
			// Concateno el caracter
			builder.append(CARACTERES.charAt(caracter));
		}
		
		// Devuelvo la cadena entera
		return builder.toString();
	}
}
