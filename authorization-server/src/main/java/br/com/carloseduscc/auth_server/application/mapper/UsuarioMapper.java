package br.com.carloseduscc.auth_server.application.mapper;

import br.com.carloseduscc.auth_server.application.command.CadastrarUsuarioCommand;
import br.com.carloseduscc.auth_server.application.dto.UsuarioDTO;
import br.com.carloseduscc.auth_server.model.Usuario;
import br.com.carloseduscc.auth_server.model.events.UsuarioCadastradoEvent;
import br.com.carloseduscc.auth_server.model.events.UsuarioDesativadoEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    Usuario fromCommand(CadastrarUsuarioCommand usuario);

    // Events
    UsuarioDesativadoEvent toUsuarioDesativadoEvent(Usuario usuario);
    UsuarioCadastradoEvent toUsuarioCadastradoEvent(Usuario usuario);
}
