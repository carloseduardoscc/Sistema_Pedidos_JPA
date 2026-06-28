package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao.SomenteAdmin;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao.SomenteClienteDonoDoRecurso;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao.SomenteClienteDonoDoRecursoOuAdmin;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AtualizarDadosUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController implements GenericController {

    private final UsuarioService service;

    @PostMapping("{usuarioId}/pedidos")
    @SomenteClienteDonoDoRecursoOuAdmin
    public ResponseEntity<Object> abrirNovoPedido(@PathVariable UUID usuarioId) {
        var response = service.abrirNovoPedido(usuarioId);
        URI uri = gerarHeaderLocation(response.id().toString());
        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping("{usuarioId}")
    @SomenteClienteDonoDoRecursoOuAdmin
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID usuarioId) {
        UsuarioDTO usuarioDTO = service.buscarDetalhes(usuarioId);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    @SomenteAdmin
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
    @SomenteClienteDonoDoRecursoOuAdmin
    public ResponseEntity<Object> atualizarDados(
            @RequestBody @Valid AtualizarDadosUsuarioCommand cmd,
            @PathVariable UUID usuarioId
    ) {
        service.atualizarDados(cmd, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{usuarioId}")
    @SomenteClienteDonoDoRecurso
    public ResponseEntity<Object> desativarUsuario(@PathVariable UUID usuarioId) {
        service.desativarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
