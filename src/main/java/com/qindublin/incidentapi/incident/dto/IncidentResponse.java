package com.qindublin.incidentapi.incident.dto;

import com.qindublin.incidentapi.incident.Incident;
import com.qindublin.incidentapi.incident.IncidentPriority;
import com.qindublin.incidentapi.incident.IncidentStatus;

import java.time.Instant;
import java.util.UUID;

public record IncidentResponse(
        UUID id,
        String title,
        String description,
        IncidentStatus status,
        IncidentPriority priority,
        String assignee,
        Instant createdAt,
        Instant updatedAt
) {

    public static IncidentResponse from(Incident incident) {
        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getStatus(),
                incident.getPriority(),
                incident.getAssignee(),
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );
    }
}
