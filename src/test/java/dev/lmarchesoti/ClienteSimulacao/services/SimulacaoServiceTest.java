package dev.lmarchesoti.ClienteSimulacao.services;

import dev.lmarchesoti.ClienteSimulacao.dto.ParcelaSimulacaoDto;
import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SimulacaoServiceTest {

    @InjectMocks
    private SimulacaoService simulacaoService;

    @Test
    @DisplayName("Deve retornar a primeira parcela quando uma simulação válida é fornecida")
    void testCalculaPrimeiraParcela_whenEFornecidaUmaSimulacaoValida_deveRetornarAPrimeiraParcela() {

        Simulacao simulacao = new Simulacao();
        simulacao.setCreatedAt(LocalDateTime.of(2024, 6, 15, 10, 30, 26));
        simulacao.setValorSolicitado(BigDecimal.valueOf(300000.0));
        simulacao.setValorGarantia(BigDecimal.valueOf(1000000.0));
        simulacao.setQtdMeses(150);
        simulacao.setTxJurosMensal(BigDecimal.valueOf(0.02));

        ParcelaSimulacaoDto primeiraParcela = simulacaoService.calculaPrimeiraParcela(simulacao);

        assertEquals(LocalDate.of(2024, 7, 15), primeiraParcela.dtVencimento(), "Data de vencimento não corresponde ao valor do cálculo");
        assertEquals(0, BigDecimal.valueOf(2000).compareTo(primeiraParcela.valorAmortizacao()), "Valor de amortização não corresponde ao cálculo");
        assertEquals(0, BigDecimal.valueOf(6000).compareTo(primeiraParcela.valorJuros()), "Valor de juros não corresponde ao cálculo");
        assertEquals(0, BigDecimal.valueOf(8000).compareTo(primeiraParcela.valorParcela()), "Valor da parcela não corresponde ao cálculo");
        assertEquals(0, BigDecimal.valueOf(298000).compareTo(primeiraParcela.saldoDevedor()), "Valor do saldo devedor não corresponde ao cálculo");

    }

}