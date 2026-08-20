package com.qindublin.incidentapi.incident.dto;

import com.qindublin.incidentapi.incident.IncidentStatus;

import jakarta.validation.constraints.NotNull;


public record UpdateStatusRequest(@NotNull IncidentStatus status) {
 

}