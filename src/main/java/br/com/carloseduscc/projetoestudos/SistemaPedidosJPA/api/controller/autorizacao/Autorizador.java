package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("autorizador")
@RequiredArgsConstructor
public class Autorizador {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public boolean podeAcessarPedido(UUID idPedido, Authentication authentication){
        CustomUser usuario = (CustomUser) authentication.getPrincipal();

        if (usuario.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }

        return pedidoRepository.existsByIdAndUsuarioId(idPedido, usuario.getId());
    }

    public boolean podeAcessarItem(UUID idItem, Authentication authentication){
        CustomUser usuario = (CustomUser) authentication.getPrincipal();

        if (usuario.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }

        return itemPedidoRepository.existsByIdAndPedidoUsuarioId(idItem, usuario.getId());
    }

}
