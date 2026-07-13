package br.com.carloseduscc.resource_server.model.events;

public record UsuarioCadastradoEvent(
        String id,
        String email,
        String nome
)
{}
