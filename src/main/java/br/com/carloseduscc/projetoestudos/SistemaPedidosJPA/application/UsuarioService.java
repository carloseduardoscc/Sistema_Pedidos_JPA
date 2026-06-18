package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.AtualizarDadosUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido.AbrirPedidoResponseDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario.UsuarioDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.PedidoMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper.UsuarioMapper;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.validator.UsuarioValidator;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegraDeNegocioException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.PedidoRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.specs.UsuarioSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    final private UsuarioRepository repository;
    final private PedidoRepository pedidoRepository;
    final private UsuarioMapper mapper;
    final private PedidoMapper pedidoMapper;
    final private UsuarioValidator validator;

    @Transactional
    public UsuarioDTO cadastrarUsuario(CadastrarUsuarioCommand usuarioCmd) {
        Usuario usuario = mapper.fromCommand(usuarioCmd);
        validator.validar(usuario);
        Usuario usuarioSalvo = repository.save(usuario);
        logger.atInfo().log("Usuário " + usuario.getId().toString() + " cadastrado");
        return mapper.toDTO(usuarioSalvo);
    }

    public UsuarioDTO buscarDetalhes(UUID id) {
        Optional<Usuario> usuarioOpt = repository.findById(id);
        Usuario usuario = usuarioOpt.orElseThrow(() -> new NaoEncontradoException("Não existe usuário com id: " + id.toString()));
        return mapper.toDTO(usuario);
    }

    public Page<UsuarioDTO> pesquisarListagem(String nome, String email, Integer numeroPagina, Integer tamanhoPagina) {
        Specification<Usuario> usuarioSpecs = UsuarioSpecs.initialize();

        if (nome != null) usuarioSpecs = usuarioSpecs.and(UsuarioSpecs.nomeLike(nome));
        if (email != null) usuarioSpecs = usuarioSpecs.and(UsuarioSpecs.emailLike(email));

        PageRequest pageable = PageRequest.of(numeroPagina, tamanhoPagina);

        return repository.findAll(usuarioSpecs, pageable).map(mapper::toDTO);

    }

    @Transactional
    public void atualizarDados(@Valid AtualizarDadosUsuarioCommand dados, UUID id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não existe usuário com id: " + id.toString()));
        if (dados.nome() != null && !usuario.getNome().equals(dados.nome())){
            usuario.setNome(dados.nome());
        }
        if (dados.email() != null && !usuario.getEmail().equals(dados.email())) {
            validator.validarEmailJaExiste(dados.email());
            usuario.setEmail(dados.email());
        }
    }

    public AbrirPedidoResponseDTO abrirNovoPedido(UUID id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não existe usuário com id: " + id.toString()));
        if (pedidoRepository.existsByUsuarioAndStatus(usuario, StatusPedido.PENDENTE)) throw new RegraDeNegocioException("Usuário já tem um pedido pendente");

        Pedido pedido = new Pedido(usuario);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoMapper.toAbrirPedidoResponseDto(pedidoSalvo);
    }

    @Transactional
    public void desativarUsuario(UUID id){
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Não existe usuário com id: " + id.toString()));
        usuario.setAtivo(false);
    }
}