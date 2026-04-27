package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    List<Pedido> findByUsuario(Usuario usuario);

    List<Pedido> findByStatus(StatusPedido status);

    @Query("""
        SELECT p
        FROM Pedido p
        JOIN p.itens i
        GROUP BY p
        HAVING SUM(i.precoUnitario * i.quantidade) > ?1
""")
    List<Pedido> buscarPedidoComTotalMaiorQue(BigDecimal valorMinimo);

    @Query("""
        SELECT p
        FROM Pedido p
        JOIN FETCH p.itens i
        WHERE p.id = :id
""")
    Optional<Pedido> buscarPedidoComItensJoinFetch(@Param("id") UUID id);

    @Modifying
    @Query("""
        UPDATE Pedido p
        SET p.status = :status
        WHERE p.id = :id
""")
    void atualizarStatus(@Param("id") UUID id, @Param("status") StatusPedido status);
}
