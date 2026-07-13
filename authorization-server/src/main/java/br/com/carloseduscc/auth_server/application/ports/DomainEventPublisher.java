package br.com.carloseduscc.auth_server.application.ports;

public interface DomainEventPublisher {
    void publish(Object event);
}
