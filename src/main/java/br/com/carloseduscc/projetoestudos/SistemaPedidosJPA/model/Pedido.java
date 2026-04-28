package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

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
        itens.add(item);
        item.setPedido(this);
    }
}
