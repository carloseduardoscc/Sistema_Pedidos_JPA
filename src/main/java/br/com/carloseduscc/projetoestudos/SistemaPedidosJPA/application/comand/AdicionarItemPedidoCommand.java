package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AdicionarItemPedidoCommand(
        @NotBlank(message = "Nome do produto é obrigatório")
        @Size(min = 2, message = "Nome do produto deve ter mais que dois caracteres")
        @Size(max = 100, message = "Nome do produto deve ter menos que 100 caracteres")
        String nomeProduto,

        @NotNull(message = "Quantidade do item é obrigatório")
        @Min(value = 1, message = "Quantidade de itens não pode ser menor que um")
        @Max(value = 100, message = "Quantidade de itens não pode ser maior que 100")
        Integer quantidade,

        @NotNull(message = "Preço unitário do item é obrigatório")
        @DecimalMin(value = "0.01", message = "Preço unitário não pode ser negativo ou zero")
        BigDecimal precoUnitario
) {
}
