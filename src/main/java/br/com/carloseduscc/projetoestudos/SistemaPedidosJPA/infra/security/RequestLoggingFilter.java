package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.security;

import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long inicio = System.currentTimeMillis();
        try {

            log.info("➡ {} {}", request.getMethod(), request.getRequestURI());

            filterChain.doFilter(request,response);

        } finally {
            long tempo = System.currentTimeMillis() - inicio;

            log.info(
                    "⬅ {} {} {} ({} ms)",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    tempo
            );
        }
    }
}
