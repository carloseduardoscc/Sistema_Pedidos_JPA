package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception.RegraDeNegocioException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.util.Formatador;
import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "id_usuario")
    private UUID idUsuario;

    // Controle de concorrência otimista
    @Version
    private Long version;

    public BigDecimal getTotal() {
        return itens.stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemPedido item) {
        validarAdicaoDeItem(item);
        itens.add(item);
        item.setPedido(this);
    }

    private void validarAdicaoDeItem(ItemPedido item) {
        if (item.getQuantidade() <= 0) {
            throw new RegraDeNegocioException("Quantidade negativa!");
        }
        if (item.getPrecoUnitario().doubleValue() < 0.01) {
            throw new RegraDeNegocioException("Preço unitário negativo ou igual a zero!");
        }
        if (item.getPrecoUnitario().doubleValue() > 10_000) {
            throw new RegraDeNegocioException("Preço unitário maior que "+Formatador.formatarDinheiro(VALOR_TOTAL_MAXIMO_PEDIDO));
        }
        double totalSomadoNovoItem = getTotal().doubleValue() + (item.getPrecoUnitario().doubleValue() * item.getQuantidade());
        if (totalSomadoNovoItem > VALOR_TOTAL_MAXIMO_PEDIDO) {
            throw new RegraDeNegocioException("Total ultrapassou " + Formatador.formatarDinheiro(VALOR_TOTAL_MAXIMO_PEDIDO));
        }
        if (status != StatusPedido.PENDENTE) {
            throw new RegraDeNegocioException("Tentou adicionar um item a um pedido que não estava pendente!");
        }
    }
}
