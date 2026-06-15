package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.PedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDetalhadoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters.RequisicaoFiltroPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController implements GenericController {

    public final PedidoService service;

    @PostMapping("{idPedido}/itens-pedido")
    public ResponseEntity<Object> adicionarItemAoPedido(@PathVariable @Valid UUID idPedido, @RequestBody @Valid AdicionarItemPedidoCommand itemCmd){
        var dtoResponse = service.adicionarItem(idPedido, itemCmd);
        URI uri = gerarHeaderLocation(dtoResponse.idItem().toString());
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @GetMapping("{idPedido}")
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID idPedido){
        var responseDTO = service.obterDetalhes(idPedido);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<Object> pesquisarListagem(@ModelAttribute RequisicaoFiltroPedido parametros){
        Page<PedidoDTO> page = service.pesquisarListagem(parametros);
        return ResponseEntity.ok(page);
    }

}
