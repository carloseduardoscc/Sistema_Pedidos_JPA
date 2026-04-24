package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    @Transactional
    Pedido abrirPedido(UUID idUsuario) {
        Pedido pedido = new Pedido();
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuário com Id: " + idUsuario.toString() + "não encontrado"));
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.PENDENTE);

        pedidoRepository.save(pedido);

        logger.atInfo().log("Adicionado pedido " + pedido.getId().toString() + " ao usuário " + idUsuario.toString() + " ");

        return pedido;
    }

    @Transactional
    List<Pedido> buscarPorUsuarios(UUID idUsuario){
        Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new NotFoundException("Usuário com Id: " + idUsuario.toString() + "não encontrado"));

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
        Optional<Pedido> optPedido = pedidoRepository.buscarPedidocomItensJoinFetch(id);
        Pedido pedido = optPedido.orElseThrow(() -> new NotFoundException("Usuário com Id: " + id.toString() + "não encontrado"));
        return pedido;
    }

    @Transactional
    void atualizarStatusPedido(UUID id, StatusPedido novoStatus){
        pedidoRepository.atualizarStatus(id, novoStatus);
    }
}
