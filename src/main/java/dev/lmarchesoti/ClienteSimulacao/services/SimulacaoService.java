package dev.lmarchesoti.ClienteSimulacao.services;

import dev.lmarchesoti.ClienteSimulacao.dto.ParcelaSimulacaoDto;
import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import dev.lmarchesoti.ClienteSimulacao.repositories.SimulacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;

    public Page<Simulacao> findByIdCliente(Long idCliente, Pageable pageable) {
        return simulacaoRepository.findByClienteId(idCliente, pageable);
    }

    public ParcelaSimulacaoDto calculaPrimeiraParcela(Simulacao simulacao) {

        LocalDate dtVencimento = simulacao.getCreatedAt().plusMonths(1L).toLocalDate();
        BigDecimal valorAmortizacao = simulacao.getValorSolicitado().divide(BigDecimal.valueOf(simulacao.getQtdMeses()), RoundingMode.UP);
        BigDecimal valorJuros = simulacao.getValorSolicitado().multiply(simulacao.getTxJurosMensal());
        BigDecimal valorParcela = valorAmortizacao.add(valorJuros);
        BigDecimal saldoDevedor = simulacao.getValorSolicitado().subtract(valorAmortizacao);

        return new ParcelaSimulacaoDto(
                dtVencimento,
                valorAmortizacao,
                valorJuros,
                valorParcela,
                saldoDevedor
        );
    }
}
