package com.qindublin.incidentapi.incident;

import com.qindublin.incidentapi.incident.dto.CreateIncidentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(CreateIncidentRequest request) {
        Incident incident = new Incident(request.title(), request.description(), request.priority());
        return incidentRepository.save(incident);
    }

    public Incident getIncident(UUID id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new IncidentNotFoundException(id));
    }

    public Page<Incident> listIncidents(IncidentStatus status, IncidentPriority priority, Pageable pageable) {
        return incidentRepository.search(status, priority, pageable);
    }
}