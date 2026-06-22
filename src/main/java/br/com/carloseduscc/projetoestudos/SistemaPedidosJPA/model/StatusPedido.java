package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception.OperacaoNaoPermitidaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum StatusPedido {
    CANCELADO(List.of()),
    RECEBIDO(List.of()),
    ENVIADO(List.of(RECEBIDO)),
    PAGO(List.of(ENVIADO, CANCELADO)),
    PENDENTE(List.of(PAGO, CANCELADO));

    public final List<StatusPedido> proximosEstadosPossiveis;

    public void validarTransacao(StatusPedido novoStatus){
        if (!proximosEstadosPossiveis.contains(novoStatus)) lancarErro(novoStatus);
    }
    public void lancarErro(StatusPedido status){
        StringBuilder errorMessage = new StringBuilder();

        if (this.equals(status)) errorMessage.append("O pedido já está %S. ".formatted(status.toString().toLowerCase()));
        else errorMessage.append("Não é possível alterar um pedido %s para %s. ".formatted(this.toString().toLowerCase(), status.toString().toLowerCase()));

        if (this.proximosEstadosPossiveis.isEmpty()) errorMessage.append("Não existe mais nenhuma transição de estado válida para este pedido");
        else {
            errorMessage.append("\nPara este pedido somente é valida transição para: ");
            for (int i = 0; i < proximosEstadosPossiveis.size(); i++) {
                errorMessage.append(
                        i == 0? proximosEstadosPossiveis.get(i).toString().toLowerCase() : " e " + proximosEstadosPossiveis.get(i).toString().toLowerCase()
                );
            }
        }

        throw new OperacaoNaoPermitidaException(errorMessage.toString());
    }
}
