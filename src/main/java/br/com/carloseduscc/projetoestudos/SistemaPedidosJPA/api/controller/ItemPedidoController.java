package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao.SomenteDonoItemOuAdmin;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.ItemPedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AtualizarItemCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController implements GenericController {

    public final ItemPedidoService service;

    @GetMapping("{itemId}")
    @SomenteDonoItemOuAdmin
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID itemId){
        var responseDTO = service.buscarDetalhes(itemId);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("{itemId}")
    @SomenteDonoItemOuAdmin
    public ResponseEntity<Object> atualizarItem(@PathVariable UUID itemId, @RequestBody @Valid AtualizarItemCommand dados){
        service.atualizarItem(dados, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{itemId}")
    @SomenteDonoItemOuAdmin
    public ResponseEntity<Object> removerItem(@PathVariable UUID itemId){
        service.removerItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
