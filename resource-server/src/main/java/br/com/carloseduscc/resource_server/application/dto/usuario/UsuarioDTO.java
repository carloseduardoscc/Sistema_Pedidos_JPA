package br.com.carloseduscc.resource_server.application.dto.usuario;


import br.com.carloseduscc.resource_server.model.Roles;

import java.util.List;

public record UsuarioDTO (
        String id,
        String nome,
        String email,
        List<Roles> roles,
        boolean ativo
) {
}
