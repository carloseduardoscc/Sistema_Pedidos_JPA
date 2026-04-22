package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception.BussinesViolationException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString(exclude = "pedido")
@Table(name = "itemPedido_tb", schema = "order_management")
@Check(constraints = "quantidade > 0 AND preco_unitario >= 0")
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nomeProduto", length = 100, nullable = false)
    private String nomeProduto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "precoUnitario", nullable = false, precision = 18, scale = 2)
    private BigDecimal precoUnitario;

    // Relação
    @ManyToOne
    private Pedido pedido;

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setQuantidade(Integer quantidade) {
        if(quantidade <= 0){
            throw new BussinesViolationException("Não são permitidas quantidades de itens de pedido menores ou iguais que zero!");
        }
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        if (precoUnitario.compareTo(BigDecimal.ZERO) < 0){
            throw new BussinesViolationException("Não são permitidos preços unitários de itens de pedido menores que zero!");
        }
        this.precoUnitario = precoUnitario;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}


