package br.com.carloseduscc.resource_server.api.controller;

import br.com.carloseduscc.resource_server.api.controller.common.GenericController;
import br.com.carloseduscc.resource_server.application.PedidoService;
import br.com.carloseduscc.resource_server.application.command.AdicionarItemPedidoCommand;
import br.com.carloseduscc.resource_server.application.command.AlterarStatusCommand;
import br.com.carloseduscc.resource_server.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.resource_server.application.query_filters.RequisicaoFiltroPedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController implements GenericController {

    public final PedidoService service;

    @PostMapping("{idPedido}/itens-pedido")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<Object> adicionarItemAoPedido(@PathVariable @Valid UUID idPedido, @RequestBody @Valid AdicionarItemPedidoCommand itemCmd){
        var dtoResponse = service.adicionarItem(idPedido, itemCmd);
        URI uri = gerarHeaderLocation(dtoResponse.idItem().toString());
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @GetMapping("{idPedido}")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id) or hasAnyRole('ADMIN', 'LOJISTA', 'ENTREGADOR')")
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID idPedido){
        var responseDTO = service.obterDetalhes(idPedido);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'LOJISTA', 'ENTREGADOR')")
    public ResponseEntity<Object> pesquisarListagem(@ModelAttribute RequisicaoFiltroPedido parametros){
        Page<PedidoDTO> page = service.pesquisarListagem(parametros);
        return ResponseEntity.ok(page);
    }

    @PatchMapping("{idPedido}/pagar")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id)")
    public ResponseEntity<Object> tornarPago(@PathVariable UUID idPedido, @RequestBody AlterarStatusCommand status){
        service.tornarPago(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idPedido}/enviar")
    @PreAuthorize("hasAnyRole('LOJISTA', 'ADMIN')")
    public ResponseEntity<Object> tornarEnviado(@PathVariable UUID idPedido, @RequestBody AlterarStatusCommand status){
        service.tornarEnviado(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idPedido}/tornar-entregue")
    @PreAuthorize("hasAnyRole('ENTREGADOR', 'ADMIN')")
    public ResponseEntity<Object> tornarEntregue(@PathVariable UUID idPedido, @RequestBody AlterarStatusCommand status){
        service.tornarEntregue(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idPedido}/cancelar")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<Object> tornarCancelado(@PathVariable UUID idPedido, @RequestBody AlterarStatusCommand status){
        service.tornarCancelado(idPedido);
        return ResponseEntity.noContent().build();
    }
}
