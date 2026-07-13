package br.com.carloseduscc.auth_server.application;


import br.com.carloseduscc.auth_server.application.command.AtualizarDadosUsuarioCommand;
import br.com.carloseduscc.auth_server.application.command.CadastrarUsuarioCommand;
import br.com.carloseduscc.auth_server.application.dto.UsuarioDTO;
import br.com.carloseduscc.auth_server.application.exception.NaoEncontradoException;
import br.com.carloseduscc.auth_server.application.mapper.UsuarioMapper;
import br.com.carloseduscc.auth_server.application.ports.DomainEventPublisher;
import br.com.carloseduscc.auth_server.application.validator.UsuarioValidator;
import br.com.carloseduscc.auth_server.model.Usuario;
import br.com.carloseduscc.auth_server.infra.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    final private UsuarioRepository repository;
    final private UsuarioMapper mapper;
    final private UsuarioValidator validator;
    final private DomainEventPublisher eventPublisher;
    final private PasswordEncoder encoder;

    @Transactional
    public UsuarioDTO cadastrarUsuario(CadastrarUsuarioCommand usuarioCmd) {
        Usuario usuario = mapper.fromCommand(usuarioCmd);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        validator.validarCadastro(usuario);
        Usuario usuarioSalvo = repository.save(usuario);
        eventPublisher.publish(mapper.toUsuarioCadastradoEvent(usuario));
        return mapper.toDTO(usuarioSalvo);
    }

    //todo temporário usado para cadastrar usuários autenticados pelo login social
    @Transactional
    public Usuario cadastrarUsuarioTemp(CadastrarUsuarioCommand usuarioCmd) {
        Usuario usuario = mapper.fromCommand(usuarioCmd);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        validator.validarCadastro(usuario);
        Usuario usuarioSalvo = repository.save(usuario);
        eventPublisher.publish(mapper.toUsuarioCadastradoEvent(usuario));
        return usuarioSalvo;
    }

    @Transactional
    public void atualizarDados(@Valid AtualizarDadosUsuarioCommand dados, UUID id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não existe usuário com id: " + id.toString()));
        if (dados.email() != null && !usuario.getEmail().equals(dados.email())) {
            validator.validarEmailJaExiste(dados.email());
            usuario.setEmail(dados.email());
        }
    }

    public Optional<Usuario> obterPorLogin(String login) {
        return repository.findByEmail(login);
    }
}