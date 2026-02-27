package dev.lmarchesoti.ClienteSimulacao.services;

import dev.lmarchesoti.ClienteSimulacao.model.Cliente;
import dev.lmarchesoti.ClienteSimulacao.model.Endereco;
import dev.lmarchesoti.ClienteSimulacao.model.enums.EstadoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("CalculaNomesClientesParaCidadeEstado retorna os clientes da cidade e estado informado quando existem")
    void testCalculaNomesClientesParaCidadeEstado_whenClientesExistWithInformedCidadeEEstado_shouldReturnMatchingClientes() {
        Endereco endereco1 = new Endereco();
        endereco1.setEstado(EstadoEnum.MG);
        endereco1.setCidade("Uberlândia");
        Cliente cliente1 = new Cliente();
        cliente1.setEndereco(endereco1);

        Endereco endereco2 = new Endereco();
        endereco2.setEstado(EstadoEnum.SP);
        endereco2.setCidade("São Paulo");
        Cliente cliente2 = new Cliente();
        cliente2.setEndereco(endereco2);

        List<Cliente> clientes = List.of(cliente1, cliente2);

        String estado = "SP";
        String cidade = "São Paulo";

        List<String> nomesClientes = clienteService.calculaNomesClientesParaCidadeEstado(clientes, cidade, estado);

        assertNotNull(nomesClientes, "Returned list should not be null");
        assertEquals(1, nomesClientes.size(), "Should have returned exactly 1 match");

    }
}