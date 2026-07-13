package br.com.carloseduscc.auth_server.infra.repository;

import br.com.carloseduscc.auth_server.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
    UUID id(UUID id);

    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
