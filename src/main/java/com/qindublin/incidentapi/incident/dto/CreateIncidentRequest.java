package com.qindublin.incidentapi.incident.dto;

import com.qindublin.incidentapi.incident.IncidentPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIncidentRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull IncidentPriority priority
) {
}