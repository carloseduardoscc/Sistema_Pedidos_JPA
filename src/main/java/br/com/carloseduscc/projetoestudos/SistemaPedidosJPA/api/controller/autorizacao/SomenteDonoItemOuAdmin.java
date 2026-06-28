package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller.autorizacao;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//{@link ElementType java.lang.annotation.ElementType}

/**Esta anotation faz o spring security verificar se o usuário autenticado é um usuário {@code CLIENTE} dono do item ou um usuário {@code ADMIN}, caso não seja retornará automaticamente um {@link org.springframework.security.access.AccessDeniedException}
 *
 * <p>O método que estiver anotado com esta anotation deve conter um parâmetro {@code UUID.CLASS} com o nome de itemId</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@autorizador.podeAcessarItem(#itemId, authentication)")
public @interface SomenteDonoItemOuAdmin {
}
