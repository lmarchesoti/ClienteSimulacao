package dev.lmarchesoti.ClienteSimulacao.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import dev.lmarchesoti.ClienteSimulacao.dto.SimulacaoDto;
import dev.lmarchesoti.ClienteSimulacao.model.Cliente;
import dev.lmarchesoti.ClienteSimulacao.model.Simulacao;
import dev.lmarchesoti.ClienteSimulacao.repositories.ClienteRepository;
import dev.lmarchesoti.ClienteSimulacao.repositories.SimulacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class SimulacaoControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SimulacaoRepository simulacaoRepository;

    private Long idCliente;

    @BeforeEach
    void setup() {
        Cliente cliente = new Cliente();
        clienteRepository.save(cliente);
        this.idCliente = cliente.getId();

        Simulacao simulacao = new Simulacao();
        simulacao.setCliente(cliente);
        simulacao.setCreatedAt(LocalDateTime.now());
        simulacao.setValorSolicitado(BigDecimal.TEN);
        simulacao.setValorGarantia(BigDecimal.TWO);
        simulacao.setQtdMeses(3);
        simulacao.setTxJurosMensal(BigDecimal.valueOf(0.02));

        simulacaoRepository.save(simulacao);
    }

    @Test
    @DisplayName("Should be able to retrieve simulacoes when valid idCliente is provided")
    void testSimulacoes_whenValidIdClienteIsProvidedAndClienteHasSimulacoes_shouldReturnAllSimulacoesBelongingToThatCliente() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/simulacoes")
                .accept(MediaType.APPLICATION_JSON)
                .param("idCliente", String.valueOf(idCliente));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus(), "Endpoint should have returned success");

        String contentAsString = response.getContentAsString();

        assertNotNull(contentAsString, "Returned content should not be null");
        assertFalse(contentAsString.isEmpty(), "Returned content should not be empty");

        JsonNode root = objectMapper.readTree(contentAsString);

        assertEquals(1, root.get("page").get("totalElements").asInt(), "Total elements returned does not match database count");

        List<SimulacaoDto> simulacoes = objectMapper.readValue(root.get("content").toString(), new TypeReference<>() {
        });

        assertEquals(1, simulacoes.size());

        SimulacaoDto simulacao = simulacoes.getFirst();
        assertEquals(BigDecimal.valueOf(10.0), simulacao.valorSolicitado(), "Entity fields do not match database");
    }
}