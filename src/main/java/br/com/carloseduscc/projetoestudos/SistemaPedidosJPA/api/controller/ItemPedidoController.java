package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.ItemPedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
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

    @GetMapping("{id}")
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID id){
        var responseDTO = service.buscarDetalhes(id);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removerItem(@PathVariable UUID id){
        service.removerItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> atualizarItem(@PathVariable UUID id, @RequestBody @Valid AtualizarItemCommand dados){
        service.atualizarItem(dados, id);
        return ResponseEntity.noContent().build();
    }
}
