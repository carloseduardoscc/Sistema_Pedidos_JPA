package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.specs;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecs {
    public static Specification<Produto> initialize() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Produto> minPreco(BigDecimal minPreco) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(
                root.get("precoUnitario"),
                minPreco
        );
    }

    public static Specification<Produto> maxPreco(BigDecimal maxPreco) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(
                root.get("precoUnitario"),
                maxPreco
        );
    }

    public static Specification<Produto> nomeLike(String nome){
        return (root, query, cb) -> cb.like(
                cb.upper(root.get("nome")),
                "%"+nome.toUpperCase()+"%"
        );
    }
}
