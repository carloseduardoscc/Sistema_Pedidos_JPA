package br.com.carloseduscc.auth_server.application.dto;


import br.com.carloseduscc.auth_server.model.Roles;

import java.util.List;

public record UsuarioDTO(
        String id,
        String email,
        List<Roles> roles,
        boolean ativo
) {
}
