package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Roles;

import java.util.List;

public record UsuarioDTO (
        String id,
        String nome,
        String email,
        List<Roles> roles,
        boolean ativo
) {
}
