package br.com.carloseduscc.auth_server.application.validator;


import br.com.carloseduscc.auth_server.application.exception.RegistroDuplicadoException;
import br.com.carloseduscc.auth_server.model.Roles;
import br.com.carloseduscc.auth_server.model.Usuario;
import br.com.carloseduscc.auth_server.infra.repository.UsuarioRepository;
import br.com.carloseduscc.auth_server.infra.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository repository;
    private final SecurityService securityService;

    public void validarCadastro(Usuario usuario) {
        validarEmailJaExiste(usuario.getEmail());

        if (
                usuario.getRoles().stream().anyMatch(Roles.getFuncionarios()::contains) &&
                        !securityService.usuarioLogadoContemRole(Roles.ADMIN)
        ) {
            throw new AccessDeniedException("Apenas usuários Administradores autenticados podem cadastrar novos funcionários");
        }
    }

    public void validarEmailJaExiste(String email) {
        if (repository.existsByEmail(email)) {
            throw new RegistroDuplicadoException("E-mail %s já existe".formatted(email));
        }
    }
}
