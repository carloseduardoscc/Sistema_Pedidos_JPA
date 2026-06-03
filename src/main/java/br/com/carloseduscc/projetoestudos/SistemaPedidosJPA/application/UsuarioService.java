package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.UsuarioDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.UsuarioMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.validator.UsuarioValidator;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    final private UsuarioRepository repository;
    final private UsuarioMapper mapper;
    final private UsuarioValidator validator;

    @Transactional
    public UsuarioDTO cadastrarUsuario(CadastrarUsuarioCommand usuarioCmd){

        Usuario usuario = mapper.fromCommand(usuarioCmd);

        validator.validar(usuario);

        Usuario usuarioSalvo = repository.save(usuario);

        logger.atInfo().log("Usuário "+usuario.getId().toString()+" cadastrado");

        return mapper.toDTO(usuarioSalvo);
    }
}
