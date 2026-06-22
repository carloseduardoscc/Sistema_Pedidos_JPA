package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.events;


import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events.PedidoAbertoEvent;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events.PedidoTeveStatusModificadoEvent;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events.UsuarioCadastradoEvent;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events.UsuarioDesativadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PedidoEventLogger {
    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PedidoTeveStatusModificadoEvent event){
        logger.atDebug().log(
                "Pedido {} mudou do status {} para {}",
                event.pedido().getId(),
                event.antigoStatus().toString().toLowerCase(),
                event.pedido().getStatus().toString().toLowerCase()
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PedidoAbertoEvent event){
        logger.atDebug().log("Um novo pedido foi aberto para o usuario {}", event.pedido().getUsuario().getEmail());
    }





}
