package com.gustavo.posto_combustivel.TipoCombustivel;

import com.gustavo.posto_combustivel.BombaCombustivel.BombaCombustivelModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Entity
@Table(name = "tipo_combustivel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoCombustivelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String nome;
    @Column(name = "preco_litro",nullable = false)
    private BigDecimal precoLitro;

    @OneToMany(mappedBy = "tipoCombustivel")
    private List<BombaCombustivelModel> bombaCombustivel;
}
