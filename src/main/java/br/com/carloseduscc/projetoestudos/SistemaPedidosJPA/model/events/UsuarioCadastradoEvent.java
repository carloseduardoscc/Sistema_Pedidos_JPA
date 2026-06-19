package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events;

public record UsuarioCadastradoEvent(
        String id,
        String email,
        String nome
)
{}
