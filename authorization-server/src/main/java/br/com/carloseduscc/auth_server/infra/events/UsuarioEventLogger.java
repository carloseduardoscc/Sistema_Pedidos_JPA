package br.com.carloseduscc.auth_server.infra.events;


import br.com.carloseduscc.auth_server.model.events.UsuarioCadastradoEvent;
import br.com.carloseduscc.auth_server.model.events.UsuarioDesativadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UsuarioEventLogger {
    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UsuarioDesativadoEvent event){
        logger.atDebug().log("Usuário desativado: id={}, email={}", event.id(), event.email());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UsuarioCadastradoEvent event){
        logger.atDebug().log("Usuário novo cadastrado: id={}, email={}", event.id(), event.email());
    }



}
