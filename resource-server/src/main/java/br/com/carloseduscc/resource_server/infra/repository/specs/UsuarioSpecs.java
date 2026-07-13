package br.com.carloseduscc.resource_server.infra.repository.specs;

import br.com.carloseduscc.resource_server.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecs {
    public static Specification<Usuario> initialize(){
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Usuario> nomeLike(String nome){
        return (root, query, cb) -> cb.like(
                cb.upper(root.get("nome")),
                "%"+nome.toUpperCase()+"%"
        );
    }

    public static Specification<Usuario> emailLike(String email){
        return (root, query, cb) -> cb.like(
                cb.upper(root.get("email")),
                "%"+email.toUpperCase()+"%"
        );
    }
}