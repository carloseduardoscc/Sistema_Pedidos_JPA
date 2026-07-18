package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.specs.PedidoSpecs.*;

@Data
public class RequisicaoFiltroPedido {

    @Schema(example = "2026-07-15T22:08:33.588Z")
    LocalDateTime dateMin;
    @Schema(example = "2026-07-18T22:08:33.588Z")
    LocalDateTime dateMax;
    @Schema(example = "carlos.eduardo@email.com")
    String usuarioEmail;
    @Schema(example = "0")
    Integer page = 0;
    @Schema(example = "10")
    Integer size = 10;
//
//    {
//        if (page == null) page = 0;
//        if (size == null) size = 10;
//    }

    public Specification<Pedido> toSpecification(){
        Specification<Pedido> spec = initialize();
        if(dateMin != null) spec = spec.and(isAfter(dateMin));
        if(dateMax != null) spec = spec.and(isBefore(dateMax));
        if(usuarioEmail != null) spec = spec.and(usuarioEmailIsLike(usuarioEmail));
        return spec;
    }
}
