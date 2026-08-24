package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.ItemAdicionadoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDetalhadoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.ItemPedidoMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.PedidoMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters.RequisicaoFiltroPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ProdutoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.UsuarioRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.*;
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
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoMapper itemPedidoMapper;
    private final PedidoMapper mapper;

    @Transactional
    public Pedido abrirPedido(UUID idUsuario) {
        Pedido pedido = new Pedido();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new NaoEncontradoException("Usuário com Id: " + idUsuario.toString() + "não encontrado"));
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.PENDENTE);

        pedidoRepository.save(pedido);

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
        Optional<Pedido> optPedido = pedidoRepository.buscarPedidoFetchProduto(id);
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
        Pedido pedido = pedidoRepository.buscarPedidoFetchProduto(id).orElseThrow(() -> new NaoEncontradoException("Pedido com Id: " + id.toString() + "não encontrado"));
        return pedido.getTotal();
    }

    @Transactional
    public ItemAdicionadoResponseDTO adicionarItem(UUID idPedido, AdicionarItemPedidoCommand itemCmd){
        Pedido pedido = pedidoRepository.buscarPedidoFetchProduto(idPedido).orElseThrow(()-> new NaoEncontradoException("Pedido com Id: " + idPedido.toString() + " não encontrado"));

        Produto produto = produtoRepository.findById(itemCmd.produtoId()).orElseThrow(()-> new NaoEncontradoException("Produto com Id: " + itemCmd.produtoId().toString() + " não encontrado"));
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(itemCmd.quantidade());

        // Caso já exista um item com o mesmo produto, apenas atualiza a quantidade
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto().getId().equals(itemCmd.produtoId())){
                pedido.validarItemNoPedido(itemPedido);
                item.setQuantidade(item.getQuantidade() + itemCmd.quantidade());
                return itemPedidoMapper.toPedidoAdicionadoResponseDTO(itemPedidoRepository.save(item));
            }
        }

        pedido.adicionarItem(itemPedido);
        ItemPedido itemSalvo = itemPedidoRepository.save(itemPedido);
        ItemAdicionadoResponseDTO respostaDTO = itemPedidoMapper.toPedidoAdicionadoResponseDTO(itemSalvo);
        return respostaDTO;
    }

    public PedidoDetalhadoDTO obterDetalhes(UUID id) {
        Pedido pedido = pedidoRepository.buscarPedidoFetchProduto(id).orElseThrow(()-> new NaoEncontradoException("Pedido com Id: " + id.toString() + " não encontrado"));
        return mapper.toDTODetalhado(pedido);
    }

    public Page<PedidoDTO> pesquisarListagem(Pageable pageable, RequisicaoFiltroPedido parametros) {
        Page<Pedido> page = pedidoRepository.findAll(parametros.toSpecification(), pageable);
        return page.map(mapper::toDTO);
    }

    public void mudarStatus(UUID id, StatusPedido statusPedido){
        Pedido pedido = pedidoRepository.buscarPedidoFetchUsuario(id).orElseThrow(() -> new NaoEncontradoException("Pedido com Id: " + id.toString() + " não encontrado"));
        StatusPedido antigoStatus = pedido.getStatus();
        pedido.setStatus(statusPedido);
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
