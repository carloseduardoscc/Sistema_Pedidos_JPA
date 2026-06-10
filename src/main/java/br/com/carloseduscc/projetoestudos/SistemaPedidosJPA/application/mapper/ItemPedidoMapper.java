package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.PedidoAdicionadoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido fromCommand(AdicionarItemPedidoCommand pedidoCmd);

    @Mapping(source="id", target = "idItem")
    @Mapping(source = "pedido.id", target = "idPedido")
    PedidoAdicionadoResponseDTO toPedidoAdicionadoResponseDTO(ItemPedido itemPedido);
}
