package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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

}
