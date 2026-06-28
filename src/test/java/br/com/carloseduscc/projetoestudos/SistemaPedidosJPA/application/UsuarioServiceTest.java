package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Test
    void cadastrarUsuarioTeste(){
        service.cadastrarUsuario(new CadastrarUsuarioCommand("João Almeida", "joao.almeida@email.com", "123456", List.of(Roles.ADMIN)));
    }

}
