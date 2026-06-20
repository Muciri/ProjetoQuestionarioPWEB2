package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/autenticacao/**").permitAll()
                        .requestMatchers("/corridas/**","/perguntas/**","/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/autenticacao/login")
                        .loginProcessingUrl("/autenticacao/login")
                        .defaultSuccessUrl("/lobby", true)
                        .failureUrl("/autenticacao/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/autenticacao/logout")
                        .logoutSuccessUrl("/autenticacao/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                .exceptionHandling(ex ->
                        ex.accessDeniedPage("/acesso-negado"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}