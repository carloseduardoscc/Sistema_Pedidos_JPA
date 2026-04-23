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
@Table(name = "pedido_tb", schema = "order_management")
@Entity
public class Pedido {

    {
        this.dataPedido = LocalDate.now();
        this.itens = new ArrayList<>();
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
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @ManyToOne
    private Usuario usuario;
}
