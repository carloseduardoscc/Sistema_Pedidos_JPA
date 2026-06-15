package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item;

import java.math.BigDecimal;

public record ItemResumoDTO(String nomeProduto, BigDecimal valor) {
}
