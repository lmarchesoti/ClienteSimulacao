package dev.lmarchesoti.ClienteSimulacao.dto;

import dev.lmarchesoti.ClienteSimulacao.model.Cliente;

public record ClienteDto(
        Long id,
        String cpf,
        String nome,
        EnderecoDto endereco
) {

    public ClienteDto(Cliente cliente) {
        this(cliente.getId(), cliente.getCpf(), cliente.getNome(), new EnderecoDto(cliente.getEndereco()));
    }
}
