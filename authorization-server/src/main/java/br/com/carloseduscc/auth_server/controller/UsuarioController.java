package br.com.carloseduscc.auth_server.controller;

import br.com.carloseduscc.auth_server.application.UsuarioService;
import br.com.carloseduscc.auth_server.application.command.AtualizarDadosUsuarioCommand;
import br.com.carloseduscc.auth_server.controller.common.GenericController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController implements GenericController {

    private final UsuarioService service;

    @PatchMapping("{usuarioId}")
    @PreAuthorize("#usuarioId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Object> atualizarDados(
            @RequestBody @Valid AtualizarDadosUsuarioCommand cmd,
            @PathVariable UUID usuarioId
    ) {
        service.atualizarDados(cmd, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
