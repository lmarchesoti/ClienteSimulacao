package dev.lmarchesoti.ClienteSimulacao.services;

import dev.lmarchesoti.ClienteSimulacao.dto.ClienteDto;
import dev.lmarchesoti.ClienteSimulacao.exceptions.ResourceNotFoundException;
import dev.lmarchesoti.ClienteSimulacao.model.Cliente;
import dev.lmarchesoti.ClienteSimulacao.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    /**
     * Calcula uma lista com os nomes dos clientes que moram em uma determinada cidade e estado.
     *
     * @param clientes lista dos clientes usados
    como base para o cálculo
     * @param cidade cidade dos clientes a serem
    recuperados
     * @param estado estado da cidade dos clientes
    a serem recuperados
     * @return Lista com os nomes dos clientes
    da lista fornecida que moram na cidade e estado fornecida.
     */
    public List<String> calculaNomesClientesParaCidadeEstado(List<Cliente> clientes, String cidade,
                                                             String estado) {
        return clientes.stream()
                .filter(cliente -> estado.equals(cliente.getEndereco().getEstado().toString())
                        && cidade.equals(cliente.getEndereco().getCidade()))
                .map(Cliente::getNome)
                .toList();
    }
}
