package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.produto.ProdutoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto fromDtoToProduto(ProdutoDTO produtoDTO);
    ProdutoDTO toDto(Produto produto);
}
