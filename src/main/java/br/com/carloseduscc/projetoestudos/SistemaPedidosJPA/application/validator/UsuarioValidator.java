package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.validator;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegistroDuplicadoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegraDeNegocioException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    final UsuarioRepository repository;
    final PedidoRepository pedidoRepository;

    public void validar(Usuario usuario){
        if (emailJaExiste(usuario)){
            throw new RegistroDuplicadoException("E-mail %s já existe".formatted(usuario.getEmail()));
        }
    }

    private boolean emailJaExiste(Usuario usuario) {
        return repository.existsByEmail(usuario.getEmail());
    }
}
