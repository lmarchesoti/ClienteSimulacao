package dev.lmarchesoti.ClienteSimulacao.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lmarchesoti.ClienteSimulacao.dto.ClienteDto;
import dev.lmarchesoti.ClienteSimulacao.dto.EnderecoDto;
import dev.lmarchesoti.ClienteSimulacao.model.enums.EstadoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClienteControllerIntegrationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private Long createdCustomerId;

    @Test
    @Order(1)
    @DisplayName("Should be able to create Cliente")
    void testCreateCliente_whenValidClienteIsProvided_shouldReturnSavedCliente() throws Exception {
        ClienteDto clienteDto = new ClienteDto(null, "11111111111", "Nome",
                new EnderecoDto("rua", 1, "bairro", "38400000", "Uberlândia", EstadoEnum.MG)
        );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus(), "Should have returned HTTP Status Code 201 - Created");

        String contentAsString = response.getContentAsString();

        assertNotNull(contentAsString, "Returned content should not be null");
        assertFalse(contentAsString.isEmpty(), "Returned content should not be empty");

        ClienteDto created = objectMapper.readValue(contentAsString, ClienteDto.class);

        assertNotNull(created.id(), "Created user should have an id");
        assertEquals("11111111111", created.cpf(), "Created entity fields should match performed request");
        assertEquals("bairro", created.endereco().bairro(), "Created entity fields should match performed request");

        createdCustomerId = created.id();
    }

    @Test
    @Order(2)
    @DisplayName("Should be able to retrieve Cliente")
    void testGetCliente_whenValidIdClienteIsProvided_shouldReturnAnExistingCliente() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/clientes/" + createdCustomerId);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus(), "Should have returned HTTP Status Code 200 - OK");

        String contentAsString = response.getContentAsString();

        assertNotNull(contentAsString, "Returned content should not be null");
        assertFalse(contentAsString.isEmpty(), "Returned content should not be empty");

        ClienteDto clienteDto = objectMapper.readValue(contentAsString, ClienteDto.class);

        assertEquals(createdCustomerId, clienteDto.id(), "Should have returned the requested entity");
        assertEquals("11111111111", clienteDto.cpf(), "Created entity fields should match performed request");
        assertEquals("bairro", clienteDto.endereco().bairro(), "Created entity fields should match performed request");
    }

    @Test
    @Order(3)
    @DisplayName("Should be able to update Cliente")
    void testUpdateCliente_whenExistingIdClienteIsProvided_shouldReturnUpdatedCliente() throws Exception {
        ClienteDto clienteDto = new ClienteDto(null, "22222222222", "Alguém",
                new EnderecoDto("abc", 2, "cdef", "38400111", "Outra Cidade", EstadoEnum.AL)
        );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/clientes/" + createdCustomerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus(), "Should have returned HTTP Status Code 200 - OK");

        String contentAsString = response.getContentAsString();

        assertNotNull(contentAsString, "Returned content should not be null");
        assertFalse(contentAsString.isEmpty(), "Returned content should not be empty");

        ClienteDto updated = objectMapper.readValue(contentAsString, ClienteDto.class);

        assertEquals(createdCustomerId, updated.id(), "Updated entity's id does not match request id");
        assertEquals("22222222222", updated.cpf(), "Updated entity field does not match request object");
        assertEquals("Outra Cidade", updated.endereco().cidade(), "Updated entity field does not match request object");
    }

    @Test
    @Order(4)
    @DisplayName("Should be able to delete Cliente")
    void testDeleteCliente_whenExistingIdClienteIsProvided_shouldDeleteCliente() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/clientes/" + createdCustomerId);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus(), "Failed to delete cliente. Error code returned.");
    }

    @Test
    @Order(5)
    @DisplayName("Should not be able to retrieve Cliente after deletion")
    void testDeleteCliente_whenClienteDeletedAndSameIdClienteIsProvided_shouldReturnNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/clientes/" + createdCustomerId);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus(), "Should have returned HTTP Status Code 200 - OK");
    }
}