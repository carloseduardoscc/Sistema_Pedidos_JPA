package br.com.carloseduscc.auth_server.model.events;

public record UsuarioCadastradoEvent(
        String id,
        String email
)
{}
