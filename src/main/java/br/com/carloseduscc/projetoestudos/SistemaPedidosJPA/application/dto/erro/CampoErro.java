package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.erro;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Campo de erro", description = "Informa um campo com erro")
public record CampoErro(
        @Schema(description = "Campo enviado que contém o erro", example = "email")
        String campo,
        @Schema(description = "Descrição do erro contido no campo", example = "E-mail inválido")
        String mensagem
) {
}
