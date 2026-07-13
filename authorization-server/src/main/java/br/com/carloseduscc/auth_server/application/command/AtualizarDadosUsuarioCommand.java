package br.com.carloseduscc.auth_server.application.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AtualizarDadosUsuarioCommand(
        @Email(message = "E-mail inválido")
        String email
) {
}
