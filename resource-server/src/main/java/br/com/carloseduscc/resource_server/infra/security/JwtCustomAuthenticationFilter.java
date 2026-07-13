package br.com.carloseduscc.resource_server.infra.security;

import br.com.carloseduscc.resource_server.application.UsuarioService;
import br.com.carloseduscc.resource_server.model.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
/** Troca o JwtAuthenticationToken já validado do contexto por CustomAuthentication **/
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(deveConverter(authentication)){
            String login = authentication.getName();
            Optional<Usuario> usuarioOpt = usuarioService.obterPorLogin(login);
            if(usuarioOpt.isPresent()){
                authentication = new CustomAuthentication(usuarioOpt.get());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean deveConverter(Authentication authentication){
        return authentication != null && authentication instanceof JwtAuthenticationToken;
    }
}