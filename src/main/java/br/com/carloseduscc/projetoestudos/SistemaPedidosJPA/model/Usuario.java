package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@ToString(exclude = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario_tb", schema = "order_management")
public class Usuario {

    {
        this.dataCadastro = LocalDate.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "email", length = 254, unique = true)
    private String email;
    private LocalDate dataCadastro;

    // Relação
    @OneToMany(mappedBy = "usuario")
    List<Pedido> pedidos;

}
