package com.qindublin.incidentapi.incident;

import com.qindublin.incidentapi.incident.dto.CreateIncidentRequest;
import com.qindublin.incidentapi.incident.dto.IncidentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController{
    private final IncidentService incidentService;
    public IncidentController(IncidentService incidentService){
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(@Valid @RequestBody CreateIncidentRequest request) {
        Incident incident = incidentService.createIncident(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(IncidentResponse.from(incident));
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncident(@PathVariable UUID id) {
        return IncidentResponse.from(incidentService.getIncident(id));
    }
}