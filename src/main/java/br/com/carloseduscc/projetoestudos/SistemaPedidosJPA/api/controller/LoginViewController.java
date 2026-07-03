package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.api.controller;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    public String paginaLogin(){
        return "login";
    }


    @GetMapping
    public String paginaHome(Authentication authentication, Model model){

        String username = authentication.getName();

        model.addAttribute("username", username);

        return "home";
    }
}
