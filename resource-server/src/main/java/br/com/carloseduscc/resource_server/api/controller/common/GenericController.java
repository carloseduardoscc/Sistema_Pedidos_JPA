package br.com.carloseduscc.resource_server.api.controller.common;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenericController {
    default URI gerarHeaderLocation (String id){
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return uri;
    }
}
