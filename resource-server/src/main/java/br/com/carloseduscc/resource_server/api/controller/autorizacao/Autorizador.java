package br.com.carloseduscc.resource_server.api.controller.autorizacao;

import br.com.carloseduscc.resource_server.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.resource_server.infra.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("autorizador")
@RequiredArgsConstructor
public class Autorizador {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public boolean isUsuarioDonoItem(UUID itemId, UUID usuarioId){
        return itemPedidoRepository.findById(itemId).get().getPedido().getUsuario().getId().equals(usuarioId);
    }

    public boolean isUsuarioDonoPedido(UUID pedidoId, UUID usuarioId){
        return pedidoRepository.findById(pedidoId).get().getUsuario().getId().equals(usuarioId);
    }

}
