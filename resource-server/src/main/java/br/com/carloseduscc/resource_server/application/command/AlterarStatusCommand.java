package br.com.carloseduscc.resource_server.application.command;

import br.com.carloseduscc.resource_server.model.StatusPedido;

public record AlterarStatusCommand(StatusPedido statusPedido) {
}
