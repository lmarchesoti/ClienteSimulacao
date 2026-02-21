package dev.lmarchesoti.ClienteSimulacao.services;

import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import dev.lmarchesoti.ClienteSimulacao.repositories.SimulacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;

    public Page<Simulacao> findByIdCliente(Long idCliente, Pageable pageable) {
        return simulacaoRepository.findByClienteId(idCliente, pageable);
    }
}
