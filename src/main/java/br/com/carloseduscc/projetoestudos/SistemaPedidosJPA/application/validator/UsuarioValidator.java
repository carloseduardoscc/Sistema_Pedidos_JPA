package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.validator;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegistroDuplicadoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegraDeNegocioException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    final UsuarioRepository repository;
    final PedidoRepository pedidoRepository;

    public void validar(Usuario usuario){
        validarEmailJaExiste(usuario.getEmail());
    }

    public void validarEmailJaExiste(String email) {
        if (repository.existsByEmail(email)){
            throw new RegistroDuplicadoException("E-mail %s já existe".formatted(email));
        }
    }

    public void validarDesativacao(Usuario usuario) {
        if (usuario.getAtivo() == false){
            throw new RegraDeNegocioException("Usuário já foi desativado");
        }
        if (pedidoRepository.existsByUsuarioAndStatusIn(usuario, List.of(StatusPedido.PAGO, StatusPedido.ENVIADO, StatusPedido.PENDENTE))){
            throw new RegraDeNegocioException("Não é permitido desativar um usuário que contém pedidos enviados, pagos ou pendentes, deve-se primeiro ter todos os pedidos finalizados como recebidos ou cancelados");
        }
    }
}
