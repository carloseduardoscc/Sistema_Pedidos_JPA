package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.PedidoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    PedidoService service;
    @Autowired
    private PedidoRepository pedidoRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    void buscarPedidosComTotalMaiorQueTest(){
        List<Pedido> pedidos = service.buscarPedidosComTotalMaiorQue(new BigDecimal("74"));
        pedidos.forEach(System.out::println);
    }

    @Test
    void buscarPedidoComItensTest(){
        Pedido pedido = service.buscarPedidosComItens(UUID.fromString("23f06425-8971-42ea-81ce-f88bc72ff1f6"));
        System.out.println("PEDIDO: ");
        System.out.println(pedido);
        System.out.println("ITENS: ");
        pedido.getItens().forEach(System.out::println);
    }

    @Test
    void atualizarStatusPedidoTest(){
        service.atualizarStatusPedido(UUID.fromString("90987654-83e5-4c47-b94b-0cbc14a6418b"), StatusPedido.PAGO);
    }

    @Test
    void calcularTotalPedidoTest(){
//        BigDecimal valorTotal = service.obterTotalPedido(UUID.fromString("33f06425-8971-42ea-81ce-f88bc72ff1f6"));
        BigDecimal valorTotal = service.obterTotalPedido(UUID.fromString("23f06425-8971-42ea-81ce-f88bc72ff1f6"));
        System.out.println("Total pedido: "+valorTotal);
    }
    @Test
    void adicionarItemTest(){
        ItemPedido novoItem = new ItemPedido();
        novoItem.setNomeProduto("Toner Kyocera Ecosys M3655");
        novoItem.setQuantidade(1);
        novoItem.setPrecoUnitario(new BigDecimal("77.81"));

        service.adicionarItem(UUID.fromString("33f06425-8971-42ea-81ce-f88bc72ff1f6"), novoItem);
    }

    @Test
    void atualizarStatusPedidoDirtyChecking(){
        service.atualizarStatusPedidoDirtyChecking(UUID.fromString("23f06425-8971-42ea-81ce-f88bc72ff1f6"), StatusPedido.PENDENTE);
    }

    /**
     * Utiliza método abre uma transação, salva entidade e lança exceção, é suposto a realizar o rollback e não persistir a entidade salva
     */
    @Test
    void testarRollbackEmExcecao(){
        Pedido pedido = new Pedido();
        service.cadastrarPedidoComItem(pedido, "Item teste", 0, new BigDecimal("-1"));
    }

    @Test
    @Transactional
    void buscarEntitdadeForaDaTransação(){
        Pedido pedido = pedidoRepository.findById(UUID.fromString("23f06425-8971-42ea-81ce-f88bc72ff1f6")).get();

        entityManager.detach(pedido);

        pedido.setStatus(StatusPedido.ENVIADO);
        entityManager.flush();
    }
}
