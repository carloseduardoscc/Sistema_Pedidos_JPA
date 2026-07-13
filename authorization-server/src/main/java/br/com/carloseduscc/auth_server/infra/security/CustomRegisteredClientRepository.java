package br.com.carloseduscc.auth_server.infra.security;

import br.com.carloseduscc.auth_server.application.ClientService;
import br.com.carloseduscc.auth_server.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
/** Carrega os clients OAuth2 do banco **/
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;
    private final ClientService service;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client clientEncontrado = service.buscarPorClientId(clientId);

        if (clientEncontrado == null){
            return null;
        }

        return RegisteredClient
                .withId(clientEncontrado.getId().toString())
                .clientId(clientEncontrado.getClientId())
                .clientSecret(clientEncontrado.getClientSecret())
                .redirectUri(clientEncontrado.getRedirectURI())
                .scope(clientEncontrado.getScope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .build();
    }

}
