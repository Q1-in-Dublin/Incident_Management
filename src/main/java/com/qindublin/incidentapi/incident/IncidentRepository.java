package com.qindublin.incidentapi.incident;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

@Query("SELECT i FROM Incident i WHERE (:status IS NULL OR i.status = :status) AND (:priority IS NULL OR i.priority = :priority)")
    Page<Incident> search(@Param("status") IncidentStatus status, @Param("priority") IncidentPriority priority, Pageable pageable);

}
