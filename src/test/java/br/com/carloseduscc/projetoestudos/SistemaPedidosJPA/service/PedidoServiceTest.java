package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    PedidosService service;

    @Test
    void abrirPedidoTest(){
        service.abrirPedido(UUID.fromString("23f06425-8971-42ea-81ce-f88bc72ff1f6"));
    }

    @Test
    void buscarPedidosPorUsuarioTest(){
        List<Pedido> pedidos = service.buscarPorUsuarios(UUID.fromString("bf22929b-b7f5-474a-a991-60e553f1d17e"));
        pedidos.forEach(System.out::println);
    }



}
