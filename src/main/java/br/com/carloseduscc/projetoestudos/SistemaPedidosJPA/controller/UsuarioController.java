package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller.common.GenericController;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.UsuarioDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.UsuarioMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController implements GenericController {

    private final UsuarioMapper mapper;
    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastrarUsuarioCommand usuarioCmd){
        UsuarioDTO usuarioSalvo = service.cadastrarUsuario(usuarioCmd);
        URI uri = gerarHeaderLocation(usuarioSalvo.id());
        return ResponseEntity.created(uri).body(usuarioSalvo);
    }
}
