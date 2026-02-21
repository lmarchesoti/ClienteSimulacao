package dev.lmarchesoti.ClienteSimulacao.controllers;

import dev.lmarchesoti.ClienteSimulacao.dto.ClienteDto;
import dev.lmarchesoti.ClienteSimulacao.model.Cliente;
import dev.lmarchesoti.ClienteSimulacao.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getCliente(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);

        if (optionalCliente.isPresent()) {
            return ResponseEntity.ok(new ClienteDto(optionalCliente.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClienteDto> createCliente(@RequestBody ClienteDto clienteDto) {

        Cliente saved = clienteService.create(clienteDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ClienteDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Long id,
                                                    @RequestBody ClienteDto clienteDto) {

        Cliente updated = clienteService.update(id, clienteDto);
        return ResponseEntity.ok(new ClienteDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
