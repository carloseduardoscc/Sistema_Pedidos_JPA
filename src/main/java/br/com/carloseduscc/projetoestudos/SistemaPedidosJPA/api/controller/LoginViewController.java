package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import org.springframework.stereotype.Controller;

@Controller
public class LoginViewController {
    public String paginaLogin(){
        return "login";
    }
}
