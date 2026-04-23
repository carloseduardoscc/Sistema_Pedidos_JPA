package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.service;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    @Autowired
    UsuarioRepository repository;

    @Transactional
    Usuario registrarUsuario(String nome, String email){
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        repository.save(usuario);

        logger.atInfo().log("Usuário "+usuario.getId().toString()+" cadastrado");

        return usuario;
    }
}
