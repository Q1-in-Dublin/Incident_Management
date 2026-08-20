package com.qindublin.incidentapi.incident;

import com.qindublin.incidentapi.incident.dto.CreateIncidentRequest;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsAndThenReadsBackAnIncident() throws Exception {
        var request = new CreateIncidentRequest(
                "Payment API returning 500s", "Spike in 5xx errors since deploy", IncidentPriority.HIGH);

        String createResponse = mockMvc.perform(post("/api/v1/incidents")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.title").value("Payment API returning 500s"))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(createResponse).get("id").asString();

        mockMvc.perform(get("/api/v1/incidents/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void createRejectsBlankTitleWith400() throws Exception {
        var request = new CreateIncidentRequest(
                "", "description", IncidentPriority.LOW);

        mockMvc.perform(post("/api/v1/incidents")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReturns404ForUnknownId() throws Exception {
        mockMvc.perform(get("/api/v1/incidents/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
