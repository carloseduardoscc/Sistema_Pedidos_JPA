package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.PedidoService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AdicionarItemPedidoCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.ItemAdicionadoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.PedidoDetalhadoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters.RequisicaoFiltroPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pedidos")
public class PedidoController implements GenericController {

    public final PedidoService service;

    @PostMapping("{idPedido}/itens-pedido")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id) or hasRole('ADMIN')")
    @Operation(
            summary = "Adicionar Item", description = "Adiciona item ao pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "Item adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor máximo do pedido excedeu 10.000", content = @Content()),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content())
    })
    public ResponseEntity<ItemAdicionadoResponseDTO> adicionarItemAoPedido(@PathVariable @Valid UUID idPedido, @RequestBody @Valid AdicionarItemPedidoCommand itemCmd){
        var dtoResponse = service.adicionarItem(idPedido, itemCmd);
        URI uri = gerarHeaderLocation(dtoResponse.idItem().toString());
        return ResponseEntity.created(uri).body(dtoResponse);
    }

    @GetMapping("{idPedido}")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id) or hasAnyRole('ADMIN', 'LOJISTA', 'ENTREGADOR')")
    @Operation(
            summary = "Buscar detalhes", description = "Busca detalhes de um pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "O pedido não foi encontrado", content = @Content())
    })
    public ResponseEntity<PedidoDetalhadoDTO> buscarDetalhes(@PathVariable UUID idPedido){
        var responseDTO = service.obterDetalhes(idPedido);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'LOJISTA', 'ENTREGADOR')")
    @Operation(
            summary = "Pesquisar", description = "Realiza pesquisa de pedidos baseado em parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso")
    })
    public ResponseEntity<Page<PedidoDTO>> pesquisarListagem(@ModelAttribute RequisicaoFiltroPedido parametros){
        Page<PedidoDTO> page = service.pesquisarListagem(parametros);
        return ResponseEntity.ok(page);
    }

    @PatchMapping("{idPedido}/pagar")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id)")
    @Operation(
            summary = "Tornar pago", description = "Realiza o processo de pagamento do pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "Pedido registrado como pago com sucesso"),
            @ApiResponse(responseCode = "404", description = "O pedido não foi encontrado", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Violação de regra de negócio: Apenas pedidos pendentes podem ser pagos pelo CLIENTE dono do pedido", content = @Content())
    })
    public ResponseEntity<Void> tornarPago(@PathVariable UUID idPedido){
        service.tornarPago(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idPedido}/enviar")
    @PreAuthorize("hasAnyRole('LOJISTA', 'ADMIN')")    @Operation(
            summary = "Tornar enviado", description = "Realiza o processo de envio do pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "Pedido registrado como enviado com sucesso"),
            @ApiResponse(responseCode = "404", description = "O pedido não foi encontrado", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Violação de regra de negócio: Apenas pedidos pagos podem ser enviados por um LOJISTA ou ADMIN", content = @Content())
    })
    public ResponseEntity<Void> tornarEnviado(@PathVariable UUID idPedido){
        service.tornarEnviado(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idPedido}/tornar-entregue")
    @PreAuthorize("hasAnyRole('ENTREGADOR', 'ADMIN')")
    @Operation(
            summary = "Tornar entregue", description = "Marca o pedido como entregue ao cliente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "Pedido registrado como entregue com sucesso"),
            @ApiResponse(responseCode = "404", description = "O pedido não foi encontrado", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Violação de regra de negócio: Apenas pedidos enviados podem ser entregues pelo seu ENTREGADOR ou ADMIN", content = @Content())
    })
    public ResponseEntity<Void> tornarEntregue(@PathVariable UUID idPedido){
        service.tornarEntregue(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idPedido}/cancelar")
    @PreAuthorize("@autorizador.isUsuarioDonoPedido(#idPedido, authentication.principal.id) or hasRole('ADMIN')")
    @Operation(
            summary = "Cancelar", description = "Realiza o cancelamento do pedido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "O pedido não foi encontrado", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Violação de regra de negócio: Apenas pedidos pagos ou pendentes podem ser cancelados pelo seu CLIENTE ou seu ADMIN", content = @Content())
    })
    public ResponseEntity<Void> tornarCancelado(@PathVariable UUID idPedido){
        service.tornarCancelado(idPedido);
        return ResponseEntity.noContent().build();
    }
}
