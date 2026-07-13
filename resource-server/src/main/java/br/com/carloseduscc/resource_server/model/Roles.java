package br.com.carloseduscc.resource_server.model;

import java.util.Set;

public enum Roles {
    ADMIN,
    CLIENTE,
    ENTREGADOR,
    LOJISTA;

    public static Set<Roles> getFuncionarios(){
        return Set.of(ADMIN, ENTREGADOR, LOJISTA);
    }

    public static Roles getRolePadrao(){
        return CLIENTE;
    }
}