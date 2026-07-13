package br.com.carloseduscc.auth_server.application;

import br.com.carloseduscc.auth_server.application.exception.NaoEncontradoException;
import br.com.carloseduscc.auth_server.infra.repository.ClientRepository;
import br.com.carloseduscc.auth_server.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        String secretCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(secretCriptografada);
        return repository.save(client);
    }

    public Client buscarPorClientId(String clientId){
        Client client = repository.findByClientId(clientId);

        if (client == null){
            throw new NaoEncontradoException("Client com ID "+clientId+" não foi encontrado");
        }

        return client;
    }

}
