package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.PedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.PedidoAdicionadoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller.common.GenericController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("pedidos/{idPedido}")
@RequiredArgsConstructor
public class PedidoController implements GenericController {

    public final PedidoService service;

    @PostMapping("itens-pedido")
    public ResponseEntity<Object> adicionarItemAoPedido(@PathVariable @Valid UUID idPedido, @RequestBody @Valid AdicionarItemPedidoCommand itemCmd){
        var dtoResponse = service.adicionarItem(idPedido, itemCmd);
        URI uri = gerarHeaderLocation(dtoResponse.idItem().toString());
        return ResponseEntity.created(uri).body(dtoResponse);
    }
}
