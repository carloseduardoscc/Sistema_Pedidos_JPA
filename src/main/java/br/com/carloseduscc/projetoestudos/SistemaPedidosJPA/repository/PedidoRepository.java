package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
