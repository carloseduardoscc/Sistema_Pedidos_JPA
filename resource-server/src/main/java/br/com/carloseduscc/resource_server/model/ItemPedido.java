package br.com.carloseduscc.resource_server.model;

import br.com.carloseduscc.resource_server.model.exception.RegraDeNegocioException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString(exclude = "pedido")
@Table(name = "item_pedido_tb", schema = "order_management")
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

    // Auditoria
    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    @Column(name = "id_usuario")
    private UUID idUsuario;

    // Controle de concorrência otimista
    @Version
    private Long version;

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setQuantidade(Integer quantidade) {
        if(quantidade <= 0){
            throw new RegraDeNegocioException("Não são permitidas quantidades de itens de pedido menores ou iguais que zero!");
        }
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        if (precoUnitario.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraDeNegocioException("Não são permitidos preços unitários de itens de pedido menores que zero!");
        }
        this.precoUnitario = precoUnitario;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public BigDecimal getValorTotal(){
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}


