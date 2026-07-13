package br.com.carloseduscc.resource_server.model.events;

public record UsuarioDesativadoEvent(
        String id,
        String email
) {}
