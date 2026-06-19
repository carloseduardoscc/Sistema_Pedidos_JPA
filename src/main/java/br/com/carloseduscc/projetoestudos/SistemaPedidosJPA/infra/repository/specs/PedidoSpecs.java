package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.specs;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PedidoSpecs {
    public static Specification<Pedido> initialize(){
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Pedido> isBefore(LocalDateTime maxDate){
        return (root, query, cb) -> {
            return cb.lessThanOrEqualTo(root.get("dataHoraPedido"), maxDate);
        };
    }

    public static Specification<Pedido> isAfter(LocalDateTime minDate){
        return (root, query, cb) -> {
            return cb.greaterThanOrEqualTo(root.get("dataHoraPedido"), minDate);
        };
    }
}
