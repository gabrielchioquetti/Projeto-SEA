package Projeto_SEA.IFSP.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    /*
     * para autenticar depois e liberar paginas
     * 
     * @Bean
     * public SecurityFilterChain configure(HttpSecurity http) throws Exception {
     * 
     * return http.authorizeHttpRequests((authorize) -> {
     * authorize.requestMatchers("/", "/cadastrar/aluno").permitAll();
     * authorize.anyRequest().authenticated();
     * })
     * .formLogin(form -> form.loginPage("/login").permitAll())
     * .build();
     * 
     * }
     */

    @Bean
    public SecurityFilterChain configure(
            HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/layout/**",
                                "/js/**",
                                "/Imagens/**",
                                "/uploads/**")
                        .permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole("CAE")

                        .requestMatchers("/professor/**")
                        .hasRole("PROFESSOR")

                        .requestMatchers("/aluno/**")
                        .hasRole("ALUNO")

                        .anyRequest()
                        .authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("prontuario")
                        .passwordParameter("senha")
                        .successHandler((request, response, authentication) -> {

                            boolean cae = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority()
                                            .equals("ROLE_CAE"));

                            if (cae) {
                                response.sendRedirect("/dashboard");
                            } else {
                                response.sendRedirect("/home/");
                            }

                        })
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
