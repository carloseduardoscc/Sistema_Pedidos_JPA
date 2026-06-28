package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.mapper;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.comand.CadastrarUsuarioCommand;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.usuario.UsuarioDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Usuario;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events.UsuarioCadastradoEvent;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events.UsuarioDesativadoEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    Usuario fromCommand(CadastrarUsuarioCommand usuario);

    // Events
    UsuarioDesativadoEvent toUsuarioDesativadoEvent(Usuario usuario);
    UsuarioCadastradoEvent toUsuarioCadastradoEvent(Usuario usuario);
}
