package dev.lmarchesoti.ClienteSimulacao.controllers;

import dev.lmarchesoti.ClienteSimulacao.dto.SimulacaoDto;
import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import dev.lmarchesoti.ClienteSimulacao.services.SimulacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulacoes")
@RequiredArgsConstructor
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    @GetMapping
    public ResponseEntity<Page<SimulacaoDto>> listSimulacoes(@RequestParam(name = "idCliente") Long idCliente,
                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Simulacao> listSimulacoes = simulacaoService.findByIdCliente(idCliente, pageable);

        return ResponseEntity.ok(listSimulacoes.map(SimulacaoDto::new));
    }
}
