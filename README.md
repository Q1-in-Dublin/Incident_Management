# Incident Management API

Backend service for creating, assigning, tracking, and resolving operational incidents. State-machine-driven status transitions, comment threads, and a full audit trail — the pattern behind tools like Jira, PagerDuty, and ServiceNow, at portfolio scale.

## Status

Independent learning / portfolio project, in progress (Phase 0: scaffold). Not deployed, not production software. See `docs/decisions/` for architecture decisions as they're made.

## Stack

Java 21 · Spring Boot 4.1.0 · Gradle · PostgreSQL · Spring Data JPA · Flyway · JUnit 5

## Running locally

_To be documented once the core vertical slice (Phase 1) lands._

## Tests

```
./gradlew test
```

## Limitations / deferred work

No authentication, no frontend, no message queue — deliberately out of scope for the initial version. See `docs/decisions/ADR-001-initial-architecture.md`.
