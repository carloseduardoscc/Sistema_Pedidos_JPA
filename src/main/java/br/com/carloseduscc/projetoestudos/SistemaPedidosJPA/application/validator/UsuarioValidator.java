package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.validator;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.CampoErro;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegistroDuplicadoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    final UsuarioRepository repository;

    public void validar(Usuario usuario){
        if (emailJaExiste(usuario)){
            throw new RegistroDuplicadoException("E-mail %s já existe".formatted(usuario.getEmail()));
        }
    }

    private boolean emailJaExiste(Usuario usuario) {
        return repository.existsByEmail(usuario.getEmail());
    }
}
