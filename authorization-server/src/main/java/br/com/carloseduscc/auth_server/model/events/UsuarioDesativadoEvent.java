package br.com.carloseduscc.auth_server.model.events;

public record UsuarioDesativadoEvent(
        String id,
        String email
) {}
