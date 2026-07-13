package br.com.carloseduscc.resource_server.application.query_filters;

import br.com.carloseduscc.resource_server.model.Pedido;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static br.com.carloseduscc.resource_server.infra.repository.specs.PedidoSpecs.*;

@Data
public class RequisicaoFiltroPedido {

    LocalDateTime dateMin;
    LocalDateTime dateMax;
    String usuarioEmail;
    Integer page = 0;
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
