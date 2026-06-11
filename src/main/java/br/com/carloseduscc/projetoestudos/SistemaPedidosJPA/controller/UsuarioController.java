package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller.common.GenericController;
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
        @RequestParam(value = "pagina", defaultValue = "0") Integer numeroPagina,
        @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ){
        Page<UsuarioDTO> pagina = service.pesquisarListagem(nome, email, numeroPagina, tamanhoPagina);
        return ResponseEntity.ok(pagina);
    }
}
