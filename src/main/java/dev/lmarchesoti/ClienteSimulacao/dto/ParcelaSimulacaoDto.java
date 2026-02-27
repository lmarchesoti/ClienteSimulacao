package dev.lmarchesoti.ClienteSimulacao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaSimulacaoDto(
        LocalDate dtVencimento,
        BigDecimal valorAmortizacao,
        BigDecimal valorJuros,
        BigDecimal valorParcela,
        BigDecimal saldoDevedor
) {
}
