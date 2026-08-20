# ADR-001: Initial Architecture

## Status

Accepted — 2026-08-20

## Context

This is a portfolio project demonstrating a real operational workflow (incident triage, assignment, status transitions, audit trail) rather than a generic CRUD demo, sized to finish in 4-6 weeks alongside full-time work.

## Decision

- **Java 21**, **Spring Boot 4.1.0** (latest stable at time of writing — the project's own guideline is "current stable release," not a pinned major version; Spring Boot 3.x has left the Initializr's supported version list entirely as of this writing).
- **Gradle** (Groovy DSL) as the build tool, generated via Spring Initializr rather than hand-written.
- **Gradle Toolchain** (`java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }`) pins the JDK the project compiles/runs with, independent of whatever JDK is active on the host machine. The Gradle daemon itself still needs a JVM 17+ to launch; that's pinned locally via a project-scoped `gradle.properties` (`org.gradle.java.home`), not a system-wide change.
- **PostgreSQL** via Spring Data JPA + Flyway for schema migrations.
- **Feature-oriented packages** (`incident`, `comment`, `audit`, `common`) instead of a global `controller/service/repository` split, so each domain concept's full stack lives together.
- **No authentication** in the initial version — deliberately deferred (see Consequences).

## Consequences

- Any interviewer-facing explanation of "why Spring Boot 4.1.0 and not 3.x" is: the project targets the current stable release, and 3.x is no longer what Spring Initializr offers as of August 2026.
- Deferring auth means the API is not safe to expose publicly with real data; Phase 4 offers a lightweight API-key option specifically for the deployed demo, not general-purpose security.
- Feature-oriented packages trade a small amount of upfront structure for long-term navigability as `comment` and `audit` are added in Phase 2.

## Alternatives considered

- **Maven** instead of Gradle: rejected, no strong reason to deviate from the project's stated default.
- **Global layered packages** (`controller/`, `service/`, `repository/`): rejected in favor of feature-oriented, since the project intentionally has more than one bounded concept (incident, comment, audit).
- **H2 in-memory DB for tests**: rejected for now to avoid diverging from the Postgres-only testing philosophy stated in the project's mentoring guidelines; scaffold-stage tests instead exclude datasource/JPA/Flyway autoconfiguration until Phase 1 wires up a real (or Testcontainers-backed) Postgres.
