package br.com.carloseduscc.auth_server.application.command;

import br.com.carloseduscc.auth_server.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CadastrarUsuarioCommand
        (
                @Email(message = "E-mail inválido")
                @NotBlank(message = "campo obrigatório")
                String email,
                @NotBlank(message = "campo obrigatório")
                @Size(min = 6, message = "a senha deve conter no mínimo 6 caracteres")
                @Size(max= 300, message = "a senha deve ter menos que 300 caracteres")
                String senha,
                @NotNull(message = "campo obrigatório")
                Set<Roles> roles
        ) {
}
