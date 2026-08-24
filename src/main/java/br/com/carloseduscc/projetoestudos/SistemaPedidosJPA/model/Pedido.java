package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception.RegraDeNegocioException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.util.Formatador;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString(exclude = "itens")
@Table(name = "pedido_tb", schema = "order_management")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pedido {

    public static final float VALOR_TOTAL_MAXIMO_PEDIDO = 10_000;

    {
        this.dataHoraPedido = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.status = StatusPedido.PENDENTE;
    }

    public Pedido(Usuario usuario) {
        this.usuario = usuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "dataHoraPedido")
    private LocalDateTime dataHoraPedido;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    // Relação
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ItemPedido> itens;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

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

    public BigDecimal getTotal() {
        return itens.stream()
                .map(i -> i.getProduto().getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemPedido item) {
        validarItemNoPedido(item);
        itens.add(item);
        item.setPedido(this);
    }

    public void validarItemNoPedido(ItemPedido item) {
        double precoUnitario = item.getProduto().getPrecoUnitario().doubleValue();
        Integer quantidade = item.getQuantidade();

        if (quantidade <= 0) {
            throw new RegraDeNegocioException("Quantidade negativa!");
        }
        if (precoUnitario < 0.01) {
            throw new RegraDeNegocioException("Preço unitário negativo ou igual a zero!");
        }
        if (precoUnitario > 10_000) {
            throw new RegraDeNegocioException("Preço unitário maior que "+Formatador.formatarDinheiro(VALOR_TOTAL_MAXIMO_PEDIDO));
        }
        double totalSomadoNovoItem = getTotal().doubleValue() + (precoUnitario * quantidade);
        if (totalSomadoNovoItem > VALOR_TOTAL_MAXIMO_PEDIDO) {
            throw new RegraDeNegocioException("Total do pedido ultrapassou " + Formatador.formatarDinheiro(VALOR_TOTAL_MAXIMO_PEDIDO));
        }
        if (status != StatusPedido.PENDENTE) {
            throw new RegraDeNegocioException("Tentou alterar o pedido que não estava pendente!");
        }
    }

    public void setStatus(StatusPedido status){
        this.status.validarTransacao(status);
        this.status = status;
    }
}
