package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    PedidosService service;

    @Autowired
    UsuarioService usuarioService;

    Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = usuarioService.registrarUsuario("teste", "test@email.com");
    }

    @Test
    void abrirPedidoTest(){
        service.abrirPedido(usuario.getId());
    }

}
