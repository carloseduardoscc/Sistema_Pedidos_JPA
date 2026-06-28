package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CadastrarUsuarioCommand
        (
                @NotBlank(message = "campo obrigatório")
                @Size(max = 100, message = "campo precisa ter menos que 100 caracteres")
                @Size(min = 2, message = "campo precisa ter mais que 2 caracteres")
                String nome,
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
