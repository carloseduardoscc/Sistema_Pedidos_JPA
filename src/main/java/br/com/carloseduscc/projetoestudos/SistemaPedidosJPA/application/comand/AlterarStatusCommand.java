package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;

public record AlterarStatusCommand(StatusPedido statusPedido) {
}
