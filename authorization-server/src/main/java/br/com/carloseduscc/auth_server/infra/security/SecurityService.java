package br.com.carloseduscc.auth_server.infra.security;


import br.com.carloseduscc.auth_server.model.Roles;
import br.com.carloseduscc.auth_server.model.Usuario;
import br.com.carloseduscc.auth_server.infra.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioRepository repository;

    public Usuario obterUsuarioLogado() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Usuario usuario) {
            return usuario;
        }
        return null;
    }

    public boolean usuarioLogadoContemRole(Roles role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(role.toString()));
    }
}
