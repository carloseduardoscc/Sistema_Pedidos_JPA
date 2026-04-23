package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Test
    void cadastrarUsuarioTeste(){
        service.registrarUsuario("João Almeida", "joao.almeida@email.com");
    }

}
