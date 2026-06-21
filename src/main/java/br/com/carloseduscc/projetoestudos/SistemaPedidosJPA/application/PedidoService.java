package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.ItemAdicionadoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDetalhadoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.ItemPedidoMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.PedidoMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters.RequisicaoFiltroPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ItemPedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    @Transactional
    public Pedido abrirPedido(UUID idUsuario) {
        Pedido pedido = new Pedido();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new NaoEncontradoException("Usuário com Id: " + idUsuario.toString() + "não encontrado"));
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.PENDENTE);

        pedidoRepository.save(pedido);

        logger.atInfo().log("Adicionado pedido " + pedido.getId().toString() + " ao usuário " + idUsuario.toString() + " ");

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
        logger.atInfo().log("Pedido salvo!");

        ItemPedido item = new ItemPedido();
        item.setNomeProduto(nomeItem);
        item.setQuantidade(quantidadeItem);
        item.setPrecoUnitario(precounitarioItem);

        pedido.adicionarItem(item);

        itemPedidoRepository.save(item);
        logger.atInfo().log("Item do pedido salvo!");
    }

    public PedidoDetalhadoDTO obterDetalhes(UUID idPedido) {
        Pedido pedido = pedidoRepository.buscarPedidoComItensJoinFetch(idPedido).orElseThrow(()-> new NaoEncontradoException("Pedido com Id: " + idPedido.toString() + " não encontrado"));
        return mapper.toDTODetalhado(pedido);
    }

    public Page<PedidoDTO> pesquisarListagem(RequisicaoFiltroPedido parametros) {
        Pageable pageable = PageRequest.of(parametros.getPage(), parametros.getSize());
        Page<Pedido> page = pedidoRepository.findAll(parametros.toSpecification(), pageable);
        return page.map(mapper::toDTO);
    }

    public void removerItem (UUID idItem){

    }
}
