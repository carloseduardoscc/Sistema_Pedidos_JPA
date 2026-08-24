package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemDetalhadoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemResumoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.ItemAdicionadoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.projections.ItemPedidoDetalhadoProjection;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProdutoMapper.class)
public interface ItemPedidoMapper {
    ItemPedido fromCommand(AdicionarItemPedidoCommand pedidoCmd);

    @Mapping(target = "valor", expression = "java( itemPedido.getValorTotal() )")
    @Mapping(target = "nomeProduto", expression = "java( itemPedido.getProduto().getNome() )")
    ItemResumoDTO toResumoDTO(ItemPedido itemPedido);

    ItemDetalhadoDTO fromProjectionToDTO(ItemPedidoDetalhadoProjection projection);

    @Mapping(source="id", target = "idItem")
    @Mapping(source = "pedido.id", target = "idPedido")
    ItemAdicionadoResponseDTO toPedidoAdicionadoResponseDTO(ItemPedido itemPedido);
}
