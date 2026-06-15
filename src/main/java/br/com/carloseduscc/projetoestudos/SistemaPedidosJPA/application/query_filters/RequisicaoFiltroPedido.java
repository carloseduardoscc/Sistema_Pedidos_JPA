package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import static br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.specs.PedidoSpecs.*;

import java.time.LocalDateTime;

@Data
public class RequisicaoFiltroPedido {

    LocalDateTime dateMin;
    LocalDateTime dateMax;
    Integer page = 0;
    Integer size = 10;
//
//    {
//        if (page == null) page = 0;
//        if (size == null) size = 10;
//    }

    public Specification<Pedido> toSpecification(){
        Specification<Pedido> spec = initialize();
        if(dateMin != null) spec = isAfter(dateMin);
        if(dateMax != null) spec = isBefore(dateMax);
        return spec;
    }
}
