package br.com.carloseduscc.resource_server.application.mapper;

import br.com.carloseduscc.resource_server.application.command.AdicionarItemPedidoCommand;
import br.com.carloseduscc.resource_server.application.dto.item.ItemDetalhadoDTO;
import br.com.carloseduscc.resource_server.application.dto.item.ItemResumoDTO;
import br.com.carloseduscc.resource_server.application.dto.pedido.ItemAdicionadoResponseDTO;
import br.com.carloseduscc.resource_server.infra.repository.projections.ItemPedidoDetalhadoProjection;
import br.com.carloseduscc.resource_server.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido fromCommand(AdicionarItemPedidoCommand pedidoCmd);

    @Mapping(source="id", target = "idItem")
    @Mapping(source = "pedido.id", target = "idPedido")
    ItemAdicionadoResponseDTO toPedidoAdicionadoResponseDTO(ItemPedido itemPedido);

    @Mapping(target = "valor", expression = "java( itemPedido.getValorTotal() )")
    ItemResumoDTO toResumoDTO(ItemPedido itemPedido);

    ItemDetalhadoDTO fromProjectionToDTO(ItemPedidoDetalhadoProjection projection);
}
