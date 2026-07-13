package br.com.carloseduscc.resource_server.application.ports;

public interface DomainEventPublisher {
    void publish(Object event);
}
