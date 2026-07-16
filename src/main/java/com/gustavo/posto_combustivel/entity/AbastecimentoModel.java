package com.gustavo.posto_combustivel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "abastecimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime data;
    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;
    @Column(nullable = false)
    private BigDecimal litros;

    @ManyToOne
    @JoinColumn(name = "bomba_combustivel_id", nullable = false)
    private BombaCombustivelModel bomba;
}
