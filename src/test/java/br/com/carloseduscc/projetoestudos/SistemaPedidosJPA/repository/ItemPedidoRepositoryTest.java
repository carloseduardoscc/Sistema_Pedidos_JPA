package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.ItemPedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ItemPedidoRepositoryTest {
    @Autowired
    ItemPedidoRepository repository;

    @Test
    @Transactional
    void deveDeletarItemComPedidoPendente(){
        assertEquals( repository.deleteByIdIfPedidoPendente(UUID.fromString("10000000-0000-0000-0000-000000000008")), 1);
    }

    @Test
    @Transactional
    void deveRetornarZeroAoDeletarItemComPedidoNaoPendente(){
        assertEquals( repository.deleteByIdIfPedidoPendente(UUID.fromString("10000000-0000-0000-0000-000000000002")), 0);
    }


}
