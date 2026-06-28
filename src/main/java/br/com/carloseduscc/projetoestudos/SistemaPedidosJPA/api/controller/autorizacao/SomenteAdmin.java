package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**Esta anotation faz o spring security verificar se o usuário autenticado é um usuário {@code ADMIN}, caso não seja retornará automaticamente um {@link org.springframework.security.access.AccessDeniedException}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN')")
public @interface SomenteAdmin {
}
