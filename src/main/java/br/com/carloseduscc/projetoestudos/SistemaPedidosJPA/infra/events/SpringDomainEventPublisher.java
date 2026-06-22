package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.events;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.ports.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}
