package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.PedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AlterarStatusCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters.RequisicaoFiltroPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
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

    @PatchMapping("{idPedido}")
    public ResponseEntity<Object> alterarStatus(@PathVariable UUID idPedido, @RequestBody AlterarStatusCommand status){
        service.mudarStatus(idPedido, status);
        return ResponseEntity.noContent().build();
    }
}
