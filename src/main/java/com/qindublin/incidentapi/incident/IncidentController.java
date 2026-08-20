package com.qindublin.incidentapi.incident;

import com.qindublin.incidentapi.incident.dto.CreateIncidentRequest;
import com.qindublin.incidentapi.incident.dto.IncidentResponse;
import com.qindublin.incidentapi.incident.dto.UpdateStatusRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public Page<IncidentResponse> listIncidents(
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) IncidentPriority priority,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Incident> incidents = incidentService.listIncidents(status, priority, pageable);
        return incidents.map(IncidentResponse::from);
    }

    // TODO: @PatchMapping("/{id}/status")
    //   method signature: public IncidentResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateStatusRequest request)
    //   body: one line, IncidentResponse.from(incidentService.updateStatus(id, request.status()))
   @PatchMapping("/{id}/status")
    public IncidentResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateStatusRequest request){
        return IncidentResponse.from(incidentService.updateStatus(id, request.status()));
    }
}