package com.qindublin.incidentapi.incident;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IncidentRepositoryTest {

    @Autowired
    private IncidentRepository incidentRepository;

    @Test
    void savesAndReadsBackAllFields() {
        Incident incident = new Incident("Payment API returning 500s", "Spike in 5xx errors since deploy", IncidentPriority.HIGH);

        Incident saved = incidentRepository.saveAndFlush(incident);

        Optional<Incident> found = incidentRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getTitle()).isEqualTo("Payment API returning 500s");
        assertThat(found.get().getDescription()).isEqualTo("Spike in 5xx errors since deploy");
        assertThat(found.get().getStatus()).isEqualTo(IncidentStatus.OPEN);
        assertThat(found.get().getPriority()).isEqualTo(IncidentPriority.HIGH);
        assertThat(found.get().getAssignee()).isNull();
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getUpdatedAt()).isNotNull();
    }

    @Test
    void findByIdReturnsEmptyWhenIncidentDoesNotExist() {
        Optional<Incident> found = incidentRepository.findById(UUID.randomUUID());

        assertThat(found).isEmpty();
    }
}
