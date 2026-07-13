package br.com.carloseduscc.resource_server.application;

import br.com.carloseduscc.resource_server.application.command.CadastrarUsuarioCommand;
import br.com.carloseduscc.resource_server.model.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Test
    void cadastrarUsuarioTeste(){
        service.cadastrarUsuario(new CadastrarUsuarioCommand("João Almeida", "joao.almeida@email.com", "123456", Set.of(Roles.ADMIN)));
    }

}
