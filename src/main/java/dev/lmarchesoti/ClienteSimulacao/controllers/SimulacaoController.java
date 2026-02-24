package dev.lmarchesoti.ClienteSimulacao.controllers;

import dev.lmarchesoti.ClienteSimulacao.dto.SimulacaoDto;
import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import dev.lmarchesoti.ClienteSimulacao.services.SimulacaoService;
import dev.lmarchesoti.ClienteSimulacao.util.CsvUtils;
import dev.lmarchesoti.ClienteSimulacao.util.TxtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/simulacoes")
@RequiredArgsConstructor
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "text/csv", "text/plain"})
    public ResponseEntity<?> listSimulacoes(@RequestParam(name = "idCliente") Long idCliente,
                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "20") int size,
                                                             @RequestHeader("Accept") String acceptHeader) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Simulacao> listSimulacoes = simulacaoService.findByIdCliente(idCliente, pageable);
        Page<SimulacaoDto> returnPage = listSimulacoes.map(SimulacaoDto::new);

        if (acceptHeader != null && acceptHeader.contains("text/csv")) {
            String csv = CsvUtils.convertToCsv(returnPage.getContent());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simulacoes.csv")
                    .contentType(MediaType.valueOf("text/csv"))
                    .body(csv);
        } else if (acceptHeader != null && acceptHeader.contains("text/plain")) {
            String txt = TxtUtil.convertToTxt(returnPage.getContent());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simulacoes.txt")
                    .contentType(MediaType.valueOf("text/plain"))
                    .body(txt);
        } else { // default to JSON
            return ResponseEntity.ok(returnPage);
        }
    }

}
