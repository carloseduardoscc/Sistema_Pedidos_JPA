package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.projections.ItemPedidoDetalhadoProjection;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    @Query("""
    select
        i.id as itemId,
        p.id as pedidoId,
        i.nomeProduto as nomeProduto,
        i.quantidade as quantidade,
        i.precoUnitario as precoUnitario
    from ItemPedido i
    join i.pedido p
    where i.id = :idItem
""")
    Optional<ItemPedidoDetalhadoProjection> buscarItemPedidoDetalhadoProjection(@Param("idItem") UUID id);

    @Transactional
    @Modifying
    @Query("""
    DELETE FROM ItemPedido i
    WHERE i.id = :id
    AND i.pedido.status = br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido.PENDENTE
""")
    int deleteByIdIfPedidoPendente(@Param("id") UUID id);

//    @Query("""
//    SELECT CASE
//        WHEN COUNT(i) > 0 THEN true
//        ELSE false
//    END
//    FROM ItemPedido i
//    WHERE i.id = :itemId
//        AND i.pedido.usuario.id = :usuarioId
//""")
    boolean existsByIdAndPedidoUsuarioId(UUID itemId, UUID usuarioId);
}
