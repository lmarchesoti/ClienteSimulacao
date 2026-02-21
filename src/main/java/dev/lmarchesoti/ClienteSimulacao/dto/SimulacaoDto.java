package dev.lmarchesoti.ClienteSimulacao.dto;

import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SimulacaoDto(
        Long id,
        LocalDateTime createdAt,
        BigDecimal valorSolicitado,
        BigDecimal valorGarantia,
        Integer qtdMeses,
        BigDecimal txJurosMensal
) {

    public SimulacaoDto(Simulacao simulacao) {
        this(simulacao.getId(), simulacao.getCreatedAt(), simulacao.getValorSolicitado(),
                simulacao.getValorGarantia(), simulacao.getQtdMeses(), simulacao.getTxJurosMensal());
    }
}
