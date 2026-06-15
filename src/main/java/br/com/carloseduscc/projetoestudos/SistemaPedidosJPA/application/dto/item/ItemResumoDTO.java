package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemResumoDTO(UUID id, String nomeProduto, BigDecimal valor) {
}
