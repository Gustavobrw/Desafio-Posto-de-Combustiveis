package com.gustavo.posto_combustivel.BombaCombustivel;

import com.gustavo.posto_combustivel.Abastecimento.AbastecimentoModel;
import com.gustavo.posto_combustivel.TipoCombustivel.TipoCombustivelModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder
@Entity
@Table(name = "bomba_combustivel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BombaCombustivelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "tipo_combustivel_id")
    private TipoCombustivelModel tipoCombustivel;

    @OneToMany(mappedBy = "bomba")
    private List<AbastecimentoModel> abastecimento;
}
