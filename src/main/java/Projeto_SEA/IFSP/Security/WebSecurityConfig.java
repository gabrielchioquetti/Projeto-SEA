package Projeto_SEA.IFSP.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
 
    /* para autenticar depois e liberar paginas
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/", "/cadastrar/aluno").permitAll();
            authorize.anyRequest().authenticated();
        })
        .formLogin(form -> form.loginPage("/login").permitAll())
        .build();

    }
    */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()) // libera tudo
            .csrf(csrf -> csrf.disable()); // desativa CSRF para facilitar POSTs (opcional)

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
