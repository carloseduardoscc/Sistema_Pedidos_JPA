package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.config;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security.CustomUserDetailsService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler loginSocialSuccessHandler) {
        return http
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(List.of("*"));
                    corsConfig.setAllowedMethods(List.of("*"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .httpBasic(Customizer.withDefaults())
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/h2-console/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth -> {
                    oauth.loginPage("/login");
                    oauth.successHandler(loginSocialSuccessHandler);
                })
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService, PasswordEncoder encoder) {
        return new CustomUserDetailsService(usuarioService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }
}
