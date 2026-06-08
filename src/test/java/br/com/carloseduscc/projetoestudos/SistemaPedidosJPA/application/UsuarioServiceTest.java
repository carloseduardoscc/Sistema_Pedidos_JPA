package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Test
    void cadastrarUsuarioTeste(){
        service.cadastrarUsuario(new CadastrarUsuarioCommand("João Almeida", "joao.almeida@email.com"));
    }

}
