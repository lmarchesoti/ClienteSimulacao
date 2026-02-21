package dev.lmarchesoti.ClienteSimulacao.services;

import dev.lmarchesoti.ClienteSimulacao.dto.ClienteDto;
import dev.lmarchesoti.ClienteSimulacao.exceptions.ResourceNotFoundException;
import dev.lmarchesoti.ClienteSimulacao.model.Cliente;
import dev.lmarchesoti.ClienteSimulacao.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente create(ClienteDto clienteDto) {
        return clienteRepository.save(new Cliente(clienteDto));
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente update(Long id, ClienteDto clienteDto) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (optionalCliente.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }

        Cliente cliente = optionalCliente.get();

        cliente.merge(clienteDto);

        return clienteRepository.save(cliente);
    }

    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }

        clienteRepository.deleteById(id);
    }
}
