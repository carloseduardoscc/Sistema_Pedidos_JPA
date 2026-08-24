package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AtualizarItemCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemDetalhadoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.ItemPedidoMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.projections.ItemPedidoDetalhadoProjection;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {
    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoMapper mapper;

    public ItemDetalhadoDTO buscarDetalhes(UUID id){
        Optional<ItemPedidoDetalhadoProjection> itemOpt = repository.buscarItemPedidoDetalhadoProjection(id);
        var projection = itemOpt.orElseThrow(() -> new NaoEncontradoException("Não existe pedido com Id: " + id.toString()));
        return mapper.fromProjectionToDTO(projection);
    }


    public void removerItem (UUID idItem){
        if (!repository.existsById(idItem)) throw new NaoEncontradoException("Não existe item com id: "+idItem);
        int linhasAfetadas = repository.deleteByIdIfPedidoPendente(idItem);
        if(linhasAfetadas == 0) throw new RegraDeNegocioException("Não é possível remover um item de um pedido que já não está mais pendente");
    }

    @Transactional
    public void atualizarItem (AtualizarItemCommand dados, UUID id){
        Pedido pedido = pedidoRepository.buscarPedidoPorItemId(id).orElseThrow(() -> new NaoEncontradoException("Não existe item com Id: " + id.toString()));
        ItemPedido itemPedido = pedido.getItens().stream()
                .filter(i -> i.getId().toString().equals(id.toString()))
                .findFirst().get();

        if (dados.quantidade() != null){
            itemPedido.setQuantidade(dados.quantidade());
            pedido.validarItemNoPedido(itemPedido);
        }
    }
}
