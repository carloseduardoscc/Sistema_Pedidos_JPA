package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegraDeNegocioException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class Pedido {

    public static final float VALOR_TOTAL_MAXIMO_PEDIDO = 10_000;

    {
        this.dataPedido = LocalDate.now();
        this.itens = new ArrayList<>();
        this.status = StatusPedido.PENDENTE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "dataPedido")
    private LocalDate dataPedido;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    // Relação
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ItemPedido> itens;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    public BigDecimal getTotal(){
        return itens.stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemPedido item){
        validarAdicaoDeItem(item);
        itens.add(item);
        item.setPedido(this);
    }

    private void validarAdicaoDeItem(ItemPedido item) {
        if (item.getQuantidade() <= 0){
            throw new RegraDeNegocioException("Quantidade negativa!");
        }
        if (item.getPrecoUnitario().doubleValue() < 0.01 ){
            throw new RegraDeNegocioException("Preço unitário negativo!");
        }
        if (item.getPrecoUnitario().doubleValue() > 10_000 ){
            throw new RegraDeNegocioException("Preço unitário maior que R$10.000!");
        }
        double totalSomadoNovoItem = getTotal().doubleValue() + (item.getPrecoUnitario().doubleValue() * item.getQuantidade());
        if (totalSomadoNovoItem > VALOR_TOTAL_MAXIMO_PEDIDO){
            throw new RegraDeNegocioException("Total ultrapassou R$"+VALOR_TOTAL_MAXIMO_PEDIDO);
        }
        if (status != StatusPedido.PENDENTE){
            throw new RegraDeNegocioException("Tentou adicionar um item a um pedido que não estava pendente!");
        }
    }
}
