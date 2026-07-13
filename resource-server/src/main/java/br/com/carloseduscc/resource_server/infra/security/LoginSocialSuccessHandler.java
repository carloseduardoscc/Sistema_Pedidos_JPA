package br.com.carloseduscc.resource_server.infra.security;

import br.com.carloseduscc.resource_server.application.UsuarioService;
import br.com.carloseduscc.resource_server.application.command.CadastrarUsuarioCommand;
import br.com.carloseduscc.resource_server.model.Roles;
import br.com.carloseduscc.resource_server.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService usuarioService;
    private final String SENHA_PADRAO_NOVO_USUARIO = "123456";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken){
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

            String email = oAuth2User.getAttribute("email");

            Optional<Usuario> usuarioEncontradoOpt = usuarioService.obterPorLogin(email);

            Usuario usuario = usuarioEncontradoOpt.orElseGet(() -> criarEPersistirNovoUsuario(oAuth2User));

            CustomAuthentication customAuthentication = new CustomAuthentication(usuario);

            SecurityContextHolder.getContext().setAuthentication(customAuthentication);

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private Usuario criarEPersistirNovoUsuario(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        return usuarioService.cadastrarUsuarioTemp(new CadastrarUsuarioCommand(
                gerarNomeApartirDeEmail(email),
                email,
                SENHA_PADRAO_NOVO_USUARIO,
                Set.of(Roles.getRolePadrao())
        ));
    }

    private String gerarNomeApartirDeEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}

