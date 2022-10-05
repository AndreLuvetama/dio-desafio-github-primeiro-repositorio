package me.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dio.sacola.enumaration.FormaPagamento;

import javax.persistence.*;
import java.util.List;


@Builder
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sacola {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch =FetchType.LAZY, optional = false) // Um cliente pode ter uma ou mais sacolas
    @JsonIgnore
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itens;
    private Double valorTotal;

    @Enumerated
    private FormaPagamento FormaPagamento;
    private boolean fechado;

    public Sacola() {
    }

    public Sacola(Long id, Cliente cliente, List<Item> itens, Double valorTotal, FormaPagamento formaPagamento, boolean fechado) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.valorTotal = valorTotal;
        FormaPagamento = formaPagamento;
        this.fechado = fechado;
    }
}
