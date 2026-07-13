package br.com.carloseduscc.resource_server.application;

import br.com.carloseduscc.resource_server.application.command.AdicionarItemPedidoCommand;
import br.com.carloseduscc.resource_server.application.dto.pedido.ItemAdicionadoResponseDTO;
import br.com.carloseduscc.resource_server.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.resource_server.application.dto.pedido.PedidoDetalhadoDTO;
import br.com.carloseduscc.resource_server.application.exception.NaoEncontradoException;
import br.com.carloseduscc.resource_server.application.mapper.ItemPedidoMapper;
import br.com.carloseduscc.resource_server.application.mapper.PedidoMapper;
import br.com.carloseduscc.resource_server.application.ports.DomainEventPublisher;
import br.com.carloseduscc.resource_server.application.query_filters.RequisicaoFiltroPedido;
import br.com.carloseduscc.resource_server.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.resource_server.infra.repository.PedidoRepository;
import br.com.carloseduscc.resource_server.infra.repository.UsuarioRepository;
import br.com.carloseduscc.resource_server.model.ItemPedido;
import br.com.carloseduscc.resource_server.model.Pedido;
import br.com.carloseduscc.resource_server.model.StatusPedido;
import br.com.carloseduscc.resource_server.model.Usuario;
import br.com.carloseduscc.resource_server.model.events.PedidoAbertoEvent;
import br.com.carloseduscc.resource_server.model.events.PedidoTeveStatusModificadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoMapper itemPedidoMapper;
    private final PedidoMapper mapper;
    final private DomainEventPublisher eventPublisher;

    @Transactional
    public Pedido abrirPedido(UUID idUsuario) {
        Pedido pedido = new Pedido();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new NaoEncontradoException("Usuário com Id: " + idUsuario.toString() + "não encontrado"));
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.PENDENTE);

        pedidoRepository.save(pedido);

        eventPublisher.publish(new PedidoAbertoEvent(pedido));

        return pedido;
    }

    @Transactional
    List<Pedido> buscarPorUsuarios(UUID idUsuario){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new NaoEncontradoException("Usuário com Id: " + idUsuario.toString() + "não encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario);

        return pedidos;
    }


    @Transactional
    List<Pedido> buscarPorStatus(StatusPedido statusPedido){
        List<Pedido> pedidos = pedidoRepository.findByStatus(statusPedido);
        return pedidos;
    }

    @Transactional
    List<Pedido> buscarPedidosComTotalMaiorQue(BigDecimal valorMinimo){
        List<Pedido> pedidos = pedidoRepository.buscarPedidoComTotalMaiorQue(valorMinimo);
        return pedidos;
    }

    @Transactional
    Pedido buscarPedidosComItens(UUID id){
        Optional<Pedido> optPedido = pedidoRepository.buscarPedidoComItensJoinFetch(id);
        Pedido pedido = optPedido.orElseThrow(() -> new NaoEncontradoException("Usuário com Id: " + id.toString() + "não encontrado"));
        return pedido;
    }

    @Transactional
    void atualizarStatusPedido(UUID id, StatusPedido novoStatus){
        pedidoRepository.atualizarStatus(id, novoStatus);
    }

    @Transactional
    void atualizarStatusPedidoDirtyChecking(UUID id, StatusPedido novoStatus){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Usuário com Id: " + id.toString() + " não encontrado"));
        pedido.setStatus(novoStatus);
    }

    BigDecimal obterTotalPedido(UUID id){
        Pedido pedido = pedidoRepository.buscarPedidoComItensJoinFetch(id).orElseThrow(() -> new NaoEncontradoException("Pedido com Id: " + id.toString() + "não encontrado"));
        return pedido.getTotal();
    }

    @Transactional
    public ItemAdicionadoResponseDTO adicionarItem(UUID idPedido, AdicionarItemPedidoCommand itemCmd){
        Pedido pedido = pedidoRepository.buscarPedidoComItensJoinFetch(idPedido).orElseThrow(()-> new NaoEncontradoException("Pedido com Id: " + idPedido.toString() + " não encontrado"));
        ItemPedido itemPedido = itemPedidoMapper.fromCommand(itemCmd);
        pedido.adicionarItem(itemPedido);
        ItemPedido itemSalvo = itemPedidoRepository.save(itemPedido);
        ItemAdicionadoResponseDTO respostaDTO = itemPedidoMapper.toPedidoAdicionadoResponseDTO(itemSalvo);
        return respostaDTO;
    }

    @Transactional
    void cadastrarPedidoComItem(Pedido pedido, String nomeItem, Integer quantidadeItem, BigDecimal precounitarioItem){
        pedidoRepository.save(pedido);

        ItemPedido item = new ItemPedido();
        item.setNomeProduto(nomeItem);
        item.setQuantidade(quantidadeItem);
        item.setPrecoUnitario(precounitarioItem);

        pedido.adicionarItem(item);

        itemPedidoRepository.save(item);
    }

    public PedidoDetalhadoDTO obterDetalhes(UUID id) {
        Pedido pedido = pedidoRepository.buscarPedidoComItensJoinFetch(id).orElseThrow(()-> new NaoEncontradoException("Pedido com Id: " + id.toString() + " não encontrado"));
        return mapper.toDTODetalhado(pedido);
    }

    public Page<PedidoDTO> pesquisarListagem(RequisicaoFiltroPedido parametros) {
        Pageable pageable = PageRequest.of(parametros.getPage(), parametros.getSize());
        Page<Pedido> page = pedidoRepository.findAll(parametros.toSpecification(), pageable);
        return page.map(mapper::toDTO);
    }

    public void mudarStatus(UUID id, StatusPedido statusPedido){
        Pedido pedido = pedidoRepository.buscarPedidoComUsuarioJoinFetch(id).orElseThrow(() -> new NaoEncontradoException("Pedido com Id: " + id.toString() + " não encontrado"));
        StatusPedido antigoStatus = pedido.getStatus();
        pedido.setStatus(statusPedido);

        eventPublisher.publish(new PedidoTeveStatusModificadoEvent(antigoStatus, pedido));
    }

    @Transactional
    public void tornarPago(UUID id){
        mudarStatus(id, StatusPedido.PAGO);
    }

    @Transactional
    public void tornarEnviado(UUID id){
        mudarStatus(id, StatusPedido.ENVIADO);

    }

    @Transactional
    public void tornarEntregue(UUID id){
        mudarStatus(id, StatusPedido.ENTREGUE);

    }

    @Transactional
    public void tornarCancelado(UUID id){
        mudarStatus(id, StatusPedido.CANCELADO);
    }
}
