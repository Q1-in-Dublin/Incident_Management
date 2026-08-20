package com.qindublin.incidentapi.incident;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(IncidentStatus from, IncidentStatus to) {
        super("Cannot transition incident from " + from + " to " + to);
    }
}
