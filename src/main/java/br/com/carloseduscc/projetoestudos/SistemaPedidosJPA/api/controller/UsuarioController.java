package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AtualizarDadosUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario.UsuarioDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
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

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastrarUsuarioCommand usuarioCmd){
        UsuarioDTO usuarioSalvo = service.cadastrarUsuario(usuarioCmd);
        URI uri = gerarHeaderLocation(usuarioSalvo.id());
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> buscarDetalhes(@PathVariable UUID id){
        UsuarioDTO usuarioDTO = service.buscarDetalhes(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<Object> pesquisarListagem(
        @RequestParam(value = "nome", required = false) String nome,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "page", defaultValue = "0") Integer numeroPagina,
        @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina
    ){
        Page<UsuarioDTO> pagina = service.pesquisarListagem(nome, email, numeroPagina, tamanhoPagina);
        return ResponseEntity.ok(pagina);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> atualizarDados(@RequestBody @Valid AtualizarDadosUsuarioCommand cmd, @PathVariable UUID id){
        service.atualizarDados(cmd, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/pedidos")
    public ResponseEntity<Object> abrirNovoPedido (@PathVariable UUID id){
        var response = service.abrirNovoPedido(id);
        URI uri = gerarHeaderLocation(response.id().toString());
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> desativarUsuario(@PathVariable UUID id){
        service.desativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
