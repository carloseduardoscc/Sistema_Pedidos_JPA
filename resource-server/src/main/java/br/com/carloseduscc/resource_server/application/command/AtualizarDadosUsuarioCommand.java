package br.com.carloseduscc.resource_server.application.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AtualizarDadosUsuarioCommand(
        @Size(max = 100, message = "campo precisa ter menos que 100 caracteres")
        @Size(min = 2, message = "campo precisa ter mais que 2 caracteres")
        String nome,
        @Email(message = "E-mail inválido")
        String email
) {
}
