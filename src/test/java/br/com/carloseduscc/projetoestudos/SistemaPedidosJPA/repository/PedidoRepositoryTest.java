package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class PedidoRepositoryTest {
    @Autowired
    PedidoRepository repository;


    @Test
    void buscarPedidosPorUsuarioTest(){
        Optional<Pedido> pedidoOpt = repository.buscarPedidoComItensJoinFetch(UUID.fromString("d4f8c6a9-7b3e-4f1a-a29d-8e5b0c1f73ab"));

        IO.println(pedidoOpt.get());
    }
}
