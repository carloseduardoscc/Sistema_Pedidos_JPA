package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security;

import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Data
public class CustomUser extends User {

    private final UUID id;

    public CustomUser(
            UUID id,
            String username,
            @Nullable String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(
                username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
        this.id = id;
    }
}
