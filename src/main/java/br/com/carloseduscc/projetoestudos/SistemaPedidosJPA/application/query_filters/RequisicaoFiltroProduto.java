package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.query_filters;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.specs.ProdutoSpecs;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

@Data
@Schema(description = "Requisição de filtro para busca de produtos")
public class RequisicaoFiltroProduto {

    @Schema(description = "Nome do produto para filtro (busca parcial)", example = "Notebook")
    String nome;
    @Schema(description = "Preço mínimo do produto", example = "100.00")
    BigDecimal precoMin;
    @Schema(description = "Preço máximo do produto", example = "5000.00")
    BigDecimal precoMax;

    public Specification<Produto> toSpecification() {
        if (precoMin != null && precoMax != null && precoMin.compareTo(precoMax) > 0) {
            throw new IllegalArgumentException("Filtro de preço mínimo não pode ser maior que o de preço máximo");
        }

        Specification<Produto> spec = ProdutoSpecs.initialize();

        if (nome != null) spec = spec.and(ProdutoSpecs.nomeLike(nome));
        if (precoMin != null) spec = spec.and(ProdutoSpecs.minPreco(precoMin));
        if (precoMax != null) spec = spec.and(ProdutoSpecs.maxPreco(precoMax));

        return spec;
    }
}
