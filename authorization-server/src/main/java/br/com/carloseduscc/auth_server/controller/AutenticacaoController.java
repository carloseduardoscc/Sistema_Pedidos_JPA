package br.com.carloseduscc.auth_server.controller;


import br.com.carloseduscc.auth_server.application.UsuarioService;
import br.com.carloseduscc.auth_server.application.command.CadastrarUsuarioCommand;
import br.com.carloseduscc.auth_server.application.dto.UsuarioDTO;
import br.com.carloseduscc.auth_server.controller.common.GenericController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AutenticacaoController implements GenericController {
    private final UsuarioService usuarioService;

    @PostMapping("/usuarios")
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastrarUsuarioCommand usuarioCmd){
        UsuarioDTO usuarioSalvo = usuarioService.cadastrarUsuario(usuarioCmd);
        URI uri = gerarHeaderLocation(usuarioSalvo.id());
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }
}
