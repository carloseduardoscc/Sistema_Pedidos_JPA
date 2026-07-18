package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(name = "Requisição para atualizar usuário")
public record AtualizarDadosUsuarioCommand(
        @Size(max = 100, message = "campo precisa ter menos que 100 caracteres")
        @Size(min = 2, message = "campo precisa ter mais que 2 caracteres")
        @Schema(example = "Carlos Eduardo")
        String nome,
        @Email(message = "E-mail inválido")
        @Schema(example = "carlos.eduardo@email.com")
        String email
) {
}
