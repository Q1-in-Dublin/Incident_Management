package com.qindublin.incidentapi.incident;


public enum IncidentStatus {
    OPEN, IN_PROGRESS, RESOLVED;
    public boolean canTransitionTo(IncidentStatus target){
    return switch (this){
        case OPEN -> target == IN_PROGRESS || target == RESOLVED;
        case IN_PROGRESS -> target == RESOLVED;
        case RESOLVED -> target == IN_PROGRESS;
    };
}



}