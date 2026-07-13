package br.com.carloseduscc.auth_server.infra.repository;

import br.com.carloseduscc.auth_server.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}
