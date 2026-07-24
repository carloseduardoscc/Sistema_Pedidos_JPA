package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.UsuarioRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security.CustomAuthentication;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Roles;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component("autorizador")
@RequiredArgsConstructor
public class Autorizador {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public boolean isUsuarioDonoItem(UUID itemId, UUID usuarioId) {
        return itemPedidoRepository.findById(itemId).get().getPedido().getUsuario().getId().equals(usuarioId);
    }

    public boolean isUsuarioDonoPedido(UUID pedidoId, UUID usuarioId) {
        return pedidoRepository.findById(pedidoId).get().getUsuario().getId().equals(usuarioId);
    }

    public boolean podeAbrirNovoPedido(Authentication authentication, UUID usuarioAfetadoId) {
        if (authentication instanceof CustomAuthentication customAuthentication) {
            Usuario usuarioAutenticado = customAuthentication.getUsuario();
            if (usuarioAutenticado.getRoles().contains(Roles.CLIENTE) && usuarioAutenticado.getId().equals(usuarioAfetadoId)) {
                return true;
            }

            Usuario usuarioAfetado = usuarioRepository.findById(usuarioAfetadoId).orElseThrow(() ->
                    new NaoEncontradoException("Usuário informado não existe"));

            if (usuarioAutenticado.getRoles().contains(Roles.ADMIN) && usuarioAfetado.getRoles().contains(Roles.CLIENTE)){
                return true;
            }
            return false;
        }else{
            throw new RuntimeException("Não foi registrada uma instância de CustomAuthentication no SecurityContextHolder como Authentication");
        }
    }

}
