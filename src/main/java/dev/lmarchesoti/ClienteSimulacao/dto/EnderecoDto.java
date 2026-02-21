package dev.lmarchesoti.ClienteSimulacao.dto;

import dev.lmarchesoti.ClienteSimulacao.model.Endereco;
import dev.lmarchesoti.ClienteSimulacao.model.enums.EstadoEnum;

public record EnderecoDto(
        String rua,
        Integer numero,
        String bairro,
        String CEP,
        String cidade,
        EstadoEnum estado) {

    public EnderecoDto(Endereco endereco) {
        this(endereco.getRua(), endereco.getNumero(), endereco.getBairro(), endereco.getCEP(), endereco.getCidade(), endereco.getEstado());
    }

}
