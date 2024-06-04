// Declaro el paquete al que pertenece esta clase
package daw.oliver.service.security;
/*-------------------------------------------------------------*/
// Importo las clases y anotaciones necesarias de "Spring"
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
/*-------------------------------------------------------------*/
/*
 * 
 * Clase para gestionar "Usuarios" y "Roles" desde la base de datos
 * 
 * */

@Configuration			// Para indicar que esta clase va a tener configuración de Spring
@EnableWebSecurity		// Para habilitar la seguridad en Aplicaciones Web
public class DatabaseWebSecurity {
	/*-------------------------------------------------------------*/
	// Si tenemos la anotación "@Bean" no hace falta poner "public" al método
	// "UserDetailsManager" nos permite crear usuarios que serán administrados por "Spring"
	@Bean				// En tiempo de ejecución se crea una instancia de este tipo y se queda en memoria para usarla en otros componentes
	UserDetailsManager users(DataSource dataSource) {			// El "DataSource" representa una conexión a nuestra base de datos
		// La variable "JdbcUserDetailsManager" lo que hace es administrar los detalles de los usuarios de la base de datos
		
		/* -- BASE DE DATOS POR DEFECTO -- */
		// JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		/*-------------------------------------------------------------*/
		/* -- BASE DE DATOS PERSONALIZADA -- */
		// "Spring" no conoce la estructura de las tablas, así que hay que indicar la consulta SQL para recuperar los datos
		// del usuario, y los perfiles de dicho usuario
		JdbcUserDetailsManager usuarios = new JdbcUserDetailsManager(dataSource);
		// Los nombres de los campos pueden ser cualquiera, pero el orden SI se tiene que respetar (cambiamos "enabled" por "estatus")
		usuarios.setUsersByUsernameQuery("select username,password,estatus from Usuarios u where username=?");
			
		// Consulta SQL para recuperar los perfiles asociados a cada usuario
		usuarios.setAuthoritiesByUsernameQuery("select u.username,p.perfil from UsuarioPerfil up " +
											"inner join Usuarios u on u.id = up.idUsuario " +
											"inner join Perfiles p on p.id = up.idPerfil " +
											"where u.username=?");

		// Devuelvo los usuarios
		return usuarios;
	}//end users(DataSource dataSource)
	
	// Si tenemos la anotación "@Bean" no hace falta poner "public" al método
	@Bean			// En tiempo de ejecución se crea una instancia de este tipo y se queda en memoria para usarla en otros componentes
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                // El "requestMatchers" sirve para encontrar coincidencias de peticiones
                // Permitimos el acceso a estas URL para los usuarios con "permitAll()"
                // Los recursos estáticos no requieren autenticación
                .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "/logos/**", "/css/**").permitAll()

                // Las vistas públicas no requieren autenticación
                .requestMatchers("/", "/signup", "/searchOfertas", "/bcrypt/**", "/vacantes/view/**", "/terms").permitAll()

                // Asignar permisos a URLs por ROLES
                .requestMatchers("/solicitudes/create/**", "/solicitudes/save/**").hasAnyAuthority("USUARIO")
                .requestMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR") // Cada usuario que tenga rol de "SUPERVISOR" o "ADMINISTRADOR" podrán acceder a "/vacantes"
                .requestMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")
                .requestMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
                .requestMatchers("/solicitudes/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADOR")

                // Todas las demás URLs de la aplicación requieren autenticación (todas excepto las que pusimos antes)
                .anyRequest().authenticated()

                // El formulario de "login" no requiere autenticación
                // Para personalizar nuestra propia URL usamos "loginPage()"
                // Al modificar la configuración por defecto del formulario de "login" tenemos que crear un método para "logout"
                .and().formLogin(login -> login.loginPage("/login").permitAll());

        // Devuelvo la estructura de autorizaciones creadas anteriormente
		return http.build();
	}//end filterChain(HttpSecurity http)
	
	// Método para encriptar las contraseñas de los usuarios (no se pueden desencriptar) para SEGURIDAD
	@Bean		// En tiempo de ejecución se crea nuestro "Spring Bean" y lo deja almacenado para cuando lo utilicemos
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}//end passwordEncoder()
	/*-------------------------------------------------------------*/
}//end class DatabaseWebSecurity
/*-------------------------------------------------------------*/
