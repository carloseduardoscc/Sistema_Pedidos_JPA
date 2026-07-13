package br.com.carloseduscc.resource_server.application.validator;

import br.com.carloseduscc.resource_server.application.exception.RegistroDuplicadoException;
import br.com.carloseduscc.resource_server.infra.repository.PedidoRepository;
import br.com.carloseduscc.resource_server.infra.repository.UsuarioRepository;
import br.com.carloseduscc.resource_server.infra.security.SecurityService;
import br.com.carloseduscc.resource_server.model.Roles;
import br.com.carloseduscc.resource_server.model.StatusPedido;
import br.com.carloseduscc.resource_server.model.Usuario;
import br.com.carloseduscc.resource_server.model.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository repository;
    private final PedidoRepository pedidoRepository;
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

    public void validarDesativacao(Usuario usuario) {
        if (usuario.getAtivo() == false) {
            throw new RegraDeNegocioException("Usuário já foi desativado");
        }
        if (pedidoRepository.existsByUsuarioAndStatusIn(usuario, List.of(StatusPedido.PAGO, StatusPedido.ENVIADO, StatusPedido.PENDENTE))) {
            throw new RegraDeNegocioException("Não é permitido desativar um usuário que contém pedidos enviados, pagos ou pendentes, deve-se primeiro ter todos os pedidos finalizados como entregues ou cancelados");
        }
    }
}
