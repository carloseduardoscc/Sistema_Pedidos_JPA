package br.com.carloseduscc.resource_server.application.dto.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemResumoDTO(UUID id, String nomeProduto, BigDecimal valor) {
}
