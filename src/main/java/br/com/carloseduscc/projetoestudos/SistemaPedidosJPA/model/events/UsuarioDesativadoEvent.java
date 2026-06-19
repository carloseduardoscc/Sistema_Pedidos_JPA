package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events;

public record UsuarioDesativadoEvent(
        String id,
        String email
) {}
