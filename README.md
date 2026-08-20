# Incident Management API

Backend service for creating, assigning, tracking, and resolving operational incidents. State-machine-driven status transitions, comment threads, and a full audit trail — the pattern behind tools like Jira, PagerDuty, and ServiceNow, at portfolio scale.

## Status

Independent learning / portfolio project, in progress (Phase 0: scaffold). Not deployed, not production software. See `docs/decisions/` for architecture decisions as they're made.

## Stack

Java 21 · Spring Boot 4.1.0 · Gradle · PostgreSQL · Spring Data JPA · Flyway · JUnit 5

## Running locally

1. Start Postgres (Docker Compose setup lands in Phase 3; for now, any local Postgres on the port below works).
2. Copy `.env.example` to `.env` and adjust if needed, then export it:
   ```
   export $(cat .env | xargs)
   ```
3. Run the app or tests — `spring.datasource.*` is read entirely from environment variables, no defaults are baked into `application.yml`.

## Tests

```
./gradlew test
```

## Limitations / deferred work

No authentication, no frontend, no message queue — deliberately out of scope for the initial version. See `docs/decisions/ADR-001-initial-architecture.md`.
