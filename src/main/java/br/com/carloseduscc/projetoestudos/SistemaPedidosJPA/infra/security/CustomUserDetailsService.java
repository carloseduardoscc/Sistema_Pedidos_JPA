package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Roles;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;
    private final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = service.obterPorLogin(login);

        if (usuarioOpt.isEmpty()){
            logger.atDebug().log("Tentativa de login recusada, usuário não existe:\n{}",login);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();


        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .disabled(!usuario.getAtivo())
                .roles(usuario.getRoles().stream().map(Roles::toString).collect(Collectors.joining()))
                .build();
    }
}
