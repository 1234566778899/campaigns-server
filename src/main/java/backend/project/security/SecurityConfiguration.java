package backend.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private static final String[] AUTH_WHITELIST = {
            // --sawgger-ui
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",

            //Login
            "/api/users/login/**",

            //Register
            "/api/users/register/**"
    };

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*

    1. Cuales van a ser los request que seran evaluados para saber si el usuario tiene permisos para llamarlos
        a. anyRequest -> Todos
        b. requestMatchers -> Los request de la lista input
        c. requestMatchers + HttpMethod -> Los request de la lista input pero especificamente en los metodos HTTP seleccionados

    2. Cual es la regla de autorizacion que se va aplicar sobre las rutas definidas en el punto 1
        a. permitAll()  -> Da permisos sin importar los authorities del usuario y sin importar que no esté logeado
        b. authenticated() -> Da permisos si importar los authorities del usuario pero si debe estar logeado
        c. hasAnyAuthorities() -> Da permisos solo a los usuario logeados que tienen alguno de los Authorities de la lista input
        d. denyAll() -> No da permisos a ningun usuario

     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Validar el Token
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //Configurar Conexiones Externas
        http.cors(Customizer.withDefaults()); //Permite request de servidores externos al del backend?
        http.csrf(AbstractHttpConfigurer::disable); //Permite reutilizar el token entre servidores?


        //Permisos para las rutas
        http.authorizeHttpRequests( auth-> auth
                 //       .anyRequest().permitAll()


                .requestMatchers(AUTH_WHITELIST).permitAll()

                .requestMatchers(HttpMethod.DELETE,"/api/brands/**").hasAnyAuthority("REGISTRO")
                .requestMatchers(HttpMethod.PUT,"/api/brands/**").hasAnyAuthority("REGISTRO")
                .requestMatchers(HttpMethod.POST,"/api/brands/**").hasAnyAuthority("REGISTRO")
                .requestMatchers(HttpMethod.GET,"/api/brands/**").hasAnyAuthority("CONSULTA")

                .requestMatchers(HttpMethod.PUT,"/api/cars/**").hasAnyAuthority("REGISTRO")
                .requestMatchers(HttpMethod.DELETE,"/api/cars/**").hasAnyAuthority("REGISTRO")
                .requestMatchers(HttpMethod.POST,"/api/cars/**").hasAnyAuthority("REGISTRO")
                .requestMatchers(HttpMethod.GET,"/api/cars/**").hasAnyAuthority("CONSULTA")

                .anyRequest().authenticated()


        );



        //Tipo de gestión de sesiones
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();

    }



}
