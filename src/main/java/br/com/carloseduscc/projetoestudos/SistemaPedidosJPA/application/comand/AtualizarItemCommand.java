package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AtualizarItemCommand(
        @Size(min = 2, message = "Nome do produto deve ter mais que dois caracteres")
        @Size(max = 100, message = "Nome do produto deve ter menos que 100 caracteres")
        String nomeProduto,
        @Min(value = 1, message = "Quantidade de itens não pode ser menor que um")
        @Max(value = 100, message = "Quantidade de itens não pode ser maior que 100")
        Integer quantidade,
        @DecimalMin(value = "0.01", message = "Preço unitário não pode ser negativo ou zero")
        BigDecimal precoUnitario
) {
}
