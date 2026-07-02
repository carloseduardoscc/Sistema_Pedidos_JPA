package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import java.util.Set;

public enum Roles {
    ADMIN,
    CLIENTE,
    ENTREGADOR,
    LOJISTA;

    private static Set<Roles> funcionarios = Set.of(ADMIN, ENTREGADOR, LOJISTA);
    public static Set<Roles> getFuncionarios(){
        return funcionarios;
    }
}