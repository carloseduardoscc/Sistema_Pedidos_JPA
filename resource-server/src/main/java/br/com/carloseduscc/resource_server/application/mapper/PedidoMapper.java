package br.com.carloseduscc.resource_server.application.mapper;

import br.com.carloseduscc.resource_server.application.dto.pedido.AbrirPedidoResponseDTO;
import br.com.carloseduscc.resource_server.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.resource_server.application.dto.pedido.PedidoDetalhadoDTO;
import br.com.carloseduscc.resource_server.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemPedidoMapper.class)
public interface PedidoMapper {

    @Mapping(target = "totalItens", expression = "java( pedido.getItens().size() )")
    @Mapping(target = "valorTotal", expression = "java( pedido.getTotal() )")
    PedidoDetalhadoDTO toDTODetalhado(Pedido pedido);

    PedidoDTO toDTO(Pedido pedido);

    // TO RESPONSES
    @Mapping(target = "dataPedido", source = "dataHoraPedido")
    AbrirPedidoResponseDTO toAbrirPedidoResponseDto(Pedido pedido);
}
