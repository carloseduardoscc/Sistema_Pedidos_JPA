package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario.UsuarioDTO;
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
