package br.com.wex.controller;

import br.com.wex.controller.request.PurchaseTransactionRequest;
import br.com.wex.controller.response.ConvertedTransaction;
import br.com.wex.controller.response.PurchaseTransactionResponse;
import br.com.wex.service.PurchaseTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseTransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID sampleUuid = UUID.randomUUID();
    private String sampleCurrency = "Euro";

    @Test
    public void testSave() throws Exception {
        PurchaseTransactionRequest request = Instancio.create(PurchaseTransactionRequest.class);
        PurchaseTransactionResponse response = Instancio.create(PurchaseTransactionResponse.class);

        when(service.save(any(PurchaseTransactionRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(service).save(any(PurchaseTransactionRequest.class));
    }

    @Test
    public void testGetWithConversion() throws Exception {
        ConvertedTransaction convertedTransaction = Instancio.create(ConvertedTransaction.class);

        when(service.getWithConversion(sampleUuid, sampleCurrency)).thenReturn(convertedTransaction);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/transactions/" + sampleUuid + "/convert")
                        .param("currency", sampleCurrency))
                .andExpect(status().isOk());

        verify(service).getWithConversion(sampleUuid, sampleCurrency);
    }
}