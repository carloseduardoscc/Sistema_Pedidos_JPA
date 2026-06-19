package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.ports;

public interface DomainEventPublisher {
    void publish(Object event);
}
