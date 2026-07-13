package br.com.carloseduscc.resource_server.api.controller;

import br.com.carloseduscc.resource_server.api.controller.common.GenericController;
import br.com.carloseduscc.resource_server.application.ItemPedidoService;
import br.com.carloseduscc.resource_server.application.command.AtualizarItemCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController implements GenericController {

    public final ItemPedidoService service;

    @GetMapping("{itemId}")
    @PreAuthorize("@autorizador.isUsuarioDonoItem(#itemId, authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID itemId){
        var responseDTO = service.buscarDetalhes(itemId);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("{itemId}")
    @PreAuthorize("@autorizador.isUsuarioDonoItem(#itemId, authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<Object> atualizarItem(@PathVariable UUID itemId, @RequestBody @Valid AtualizarItemCommand dados){
        service.atualizarItem(dados, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{itemId}")
    @PreAuthorize("@autorizador.isUsuarioDonoItem(#itemId, authentication.principal.id) or hasRole('ADMIN')")
    public ResponseEntity<Object> removerItem(@PathVariable UUID itemId){
        service.removerItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
