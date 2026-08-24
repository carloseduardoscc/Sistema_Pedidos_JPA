package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception.RegraDeNegocioException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "pedido")
@Table(name = "item_pedido_tb", schema = "order_management")
@Check(constraints = "quantidade > 0")
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    // Relação
    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Pedido pedido;

    // Auditoria
    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    @CreatedBy
    @Column(name = "criado_por")
    private UUID criadoPor;

    // Controle de concorrência otimista
    @Version
    private Long version;

    public void setQuantidade(Integer quantidade) {
        if(quantidade <= 0){
            throw new RegraDeNegocioException("Não são permitidas quantidades de itens de pedido menores ou iguais que zero!");
        }
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal(){
        return produto.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade));
    }
}


