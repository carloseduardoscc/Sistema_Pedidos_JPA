package br.com.carloseduscc.auth_server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping
    @RequestMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping
    @ResponseBody
    public String paginaHome(){
        return "Olá " + SecurityContextHolder.getContext().getAuthentication().getName() + " seu login foi realizado com sucesso!";
    }
}
