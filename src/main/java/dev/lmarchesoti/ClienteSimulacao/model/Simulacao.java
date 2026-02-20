package dev.lmarchesoti.ClienteSimulacao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SIMULACAO", schema = "CLIENTESIMULACAO")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SIMULACAO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE")
    private Cliente cliente;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "VALOR_SOLICITADO")
    private BigDecimal valorSolicitado;

    @Column(name = "VALOR_GARANTIA")
    private BigDecimal valorGarantia;

    @Column(name = "QTD_MESES")
    private Integer qtdMeses;

    @Column(name = "TX_JUROS_MENSAL")
    private BigDecimal txJurosMensal;

}
