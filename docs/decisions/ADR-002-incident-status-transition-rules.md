# ADR-002: Incident Status Transition Rules

## Status

Accepted — 2026-08-20

## Context

`Incident.status` must only move through a small set of deliberate transitions, not arbitrary changes. This is the rule that distinguishes the project from a plain CRUD board: a status field alone doesn't guarantee anything about *how* it got there.

Allowed transitions:

- `OPEN` → `IN_PROGRESS` or `RESOLVED`
- `IN_PROGRESS` → `RESOLVED`
- `RESOLVED` → `IN_PROGRESS` (reopen)

Any other transition (including a no-op like `OPEN` → `OPEN`) is rejected.

## Decision

- The transition rule lives on the **`IncidentStatus` enum itself** (`canTransitionTo(IncidentStatus target)`), not on the `Incident` entity and not in a separate validator class. The rule is inherent to what a status *is*, so it belongs with the enum that defines the states.
- `IncidentService.updateStatus()` is the single place that calls `canTransitionTo()` before persisting a change — the API layer never mutates status directly.
- An invalid transition throws `InvalidStatusTransitionException`, mapped by the global exception handler to **HTTP 409 Conflict** (not 400) — the request itself is well-formed, it conflicts with the incident's current state.

## Consequences

- Adding a new status later (e.g. `CLOSED`) means updating one `switch` expression in `IncidentStatus`, not hunting through service/controller code for scattered `if` checks.
- Every status change goes through `IncidentService.updateStatus()`, so there's exactly one code path to test for "invalid transitions are rejected."

## Alternatives considered

- **Validator class per transition rule set** (`IncidentStatusTransitionValidator`): rejected as unnecessary indirection for three states and four valid edges — revisit if the state machine grows meaningfully more complex.
- **400 Bad Request for invalid transitions**: rejected in favor of 409 — the request is valid input, it just can't be applied given the resource's current state, which is what 409 means.
