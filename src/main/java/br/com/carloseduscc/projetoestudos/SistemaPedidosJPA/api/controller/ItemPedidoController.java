package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.ItemPedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
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

}
