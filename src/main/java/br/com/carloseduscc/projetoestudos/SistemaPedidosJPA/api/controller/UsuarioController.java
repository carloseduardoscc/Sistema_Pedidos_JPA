package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AtualizarDadosUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.AbrirPedidoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario.UsuarioDTO;
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
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController implements GenericController {

    private final UsuarioService service;

    @PostMapping
    @Operation(
            summary = "Cadastro", description = "Realiza cadastro de usuários"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "203", description = "Cadastro realizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "O e-mail já está sendo utilizado", content = @Content()),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Apenas usuários administradores podem cadastrar novos funcionários", content = @Content())
    })
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid CadastrarUsuarioCommand usuarioCmd){
        UsuarioDTO usuarioSalvo = service.cadastrarUsuario(usuarioCmd);
        URI uri = gerarHeaderLocation(usuarioSalvo.id().toString());
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }

    @PostMapping("{usuarioId}/pedidos")
    @PreAuthorize("#usuarioId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Object> abrirNovoPedido(@PathVariable UUID usuarioId) {
        var response = service.abrirNovoPedido(usuarioId);
        URI uri = gerarHeaderLocation(response.id().toString());
        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping("{usuarioId}")
    @PreAuthorize("#usuarioId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID usuarioId) {
        UsuarioDTO usuarioDTO = service.buscarDetalhes(usuarioId);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Object> pesquisarListagem(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", defaultValue = "0") Integer numeroPagina,
            @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina
    ) {
        Page<UsuarioDTO> pagina = service.pesquisarListagem(nome, email, numeroPagina, tamanhoPagina);
        return ResponseEntity.ok(pagina);
    }

    @PatchMapping("{usuarioId}")
    @PreAuthorize("#usuarioId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Object> atualizarDados(
            @RequestBody @Valid AtualizarDadosUsuarioCommand cmd,
            @PathVariable UUID usuarioId
    ) {
        service.atualizarDados(cmd, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{usuarioId}")
    @PreAuthorize("#usuarioId == authentication.principal.id")
    public ResponseEntity<Object> desativarUsuario(@PathVariable UUID usuarioId) {
        service.desativarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
