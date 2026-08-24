package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository;


import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID>, JpaSpecificationExecutor<Produto> {
}
