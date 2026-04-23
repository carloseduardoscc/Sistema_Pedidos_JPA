package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    PedidoService service;

    @Test
    void abrirPedidoTest(){
        service.abrirPedido(UUID.fromString("23f06425-8971-42ea-81ce-f88bc72ff1f6"));
    }

    @Test
    void buscarPedidosPorUsuarioTest(){
        List<Pedido> pedidos = service.buscarPorUsuarios(UUID.fromString("bf22929b-b7f5-474a-a991-60e553f1d17e"));
        pedidos.forEach(System.out::println);
    }

    @Test
    void buscarPedidosPorStatusTest() {
        List<Pedido> pedidos = service.buscarPorStatus(StatusPedido.ENVIADO);
        pedidos.forEach(System.out::println);
    }

    @Test
    void buscarPedidosComTotalMaiorQue(){
        List<Pedido> pedidos = service.buscarPedidosComTotalMaiorQue(new BigDecimal("74"));
        pedidos.forEach(System.out::println);
    }



}
