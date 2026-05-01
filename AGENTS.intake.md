# AGENTS Upstream Intake Start-Gates

This runbook is the operational checklist for starting an upstream intake cycle after dated baselines already exist.
It complements `AGENTS.md` (canonical branch governance) and does not replace it.

## Scope and Invariants

- Date format is always `YYYY-MM-DD`.
- `integration/fork-YYYY-MM-DD` is temporary; validated work merges into `neoforge/main-clean`.
- Optional gameplay promotion is separate: `neoforge/main-clean` -> `game/main` only when ready.
- Do not perform intake-port edits on `master` or `neoforge/main-clean`.

## Gate 0 - Safety Preflight

Purpose: confirm the repository is in a safe state before intake setup.

Commands:

```bash
git fetch --all --prune
git branch --show-current
git status
```

Pass criteria:
- Current branch is not `master`.
- Current branch is not `neoforge/main-clean`.
- Worktree is clean, or all changes are intentional and understood.

Stop conditions:
- If current branch is `master` or `neoforge/main-clean`, stop and switch to a valid work branch before any edits.
- If status contains unexpected changes, stop and resolve before continuing.

## Gate 1 - Intake Identifier and Baseline Existence

Purpose: lock cycle identifiers and confirm frozen references exist.

Commands:

```bash
DATE=YYYY-MM-DD

git branch --list "baseline/fork-$DATE"
git branch --list "baseline/upstream-$DATE"
git tag --list "baseline-fork-$DATE"
git tag --list "baseline-upstream-$DATE"
```

Pass criteria:
- Both baseline branches exist:
  - `baseline/fork-$DATE`
  - `baseline/upstream-$DATE`
- Both baseline tags exist:
  - `baseline-fork-$DATE`
  - `baseline-upstream-$DATE`

Stop conditions:
- If any branch or tag is missing, stop and create/fetch the missing baseline reference before intake setup.

## Gate 2 - Clean Base Validation (`neoforge/main-clean`)

Purpose: ensure the clean upstream-equivalent lane is current and stable before branching.

Commands:

```bash
git switch neoforge/main-clean
git pull --ff-only
git status
git branch -vv
```

Pass criteria:
- `neoforge/main-clean` fast-forwards cleanly (or is already up to date).
- No unresolved merge state.
- No unexpected local divergence from upstream tracking.

Stop conditions:
- If `git pull --ff-only` fails, stop and resolve remote/local history issues before creating integration branch.
- If branch tracking/divergence is unexpected, stop and reconcile tracking first.

## Gate 3 - Integration Branch Readiness

Purpose: create or switch to the cycle integration branch and verify a clean start point.

Commands:

```bash
DATE=YYYY-MM-DD

# If branch does not exist yet:
git switch -c integration/fork-$DATE

# If branch already exists:
git switch integration/fork-$DATE

git branch --show-current
git status
git branch -vv
```

Pass criteria:
- Current branch is exactly `integration/fork-$DATE`.
- Worktree is clean before porting begins.
- Tracking/fork state is sane.

Stop conditions:
- If branch cannot be created/switched cleanly, stop and resolve naming or history conflicts.
- If worktree is dirty unexpectedly, stop and clean up before porting.

## Gate 4 - Upstream Delta Package Generation

Purpose: generate the authoritative change set from frozen baselines.

Commands:

```bash
DATE=YYYY-MM-DD

git log --oneline baseline/fork-$DATE..baseline/upstream-$DATE
git diff --stat baseline/fork-$DATE...baseline/upstream-$DATE
git diff baseline/fork-$DATE...baseline/upstream-$DATE
```

Pass criteria:
- Commit-level upstream delta is visible from `git log`.
- File-level scope is visible from `git diff --stat`.
- Full patch delta is available from `git diff`.

Stop conditions:
- If any baseline reference is unresolved, return to Gate 1 and fix references.
- If outputs are empty but changes are expected, stop and verify selected `DATE` and baseline commits.

## Gate 5 - Port-Readiness and Disposition Policy

Purpose: confirm that each upstream delta item will be tracked with a required disposition.

Required dispositions:
- `apply`
- `adapt`
- `skip/replaced`

Validation expectations before merge:
- Every upstream delta item has one disposition.
- `adapt` and `skip/replaced` entries include concise rationale.
- Integration branch validation (build/tests/manual checks used by this repo) is completed and results recorded.

Stop conditions:
- If any item lacks disposition, stop before merge.
- If validation is incomplete or failing, stop before merge.

## Gate 6 - Merge Path Confirmation

Purpose: enforce merge direction and avoid accidental lane rewrites.

Required merge path:
- `integration/fork-$DATE` -> `neoforge/main-clean`

Optional promotion path:
- `neoforge/main-clean` -> `game/main` (only when gameplay base advancement is intended)

Commands:

```bash
DATE=YYYY-MM-DD

# validated intake -> clean upstream-equivalent lane
git switch neoforge/main-clean
git merge --no-ff integration/fork-$DATE

# optional promotion step (separate decision)
git switch game/main
git merge --no-ff neoforge/main-clean
```

Pass criteria:
- No language or action treats `integration/fork-$DATE` as the new permanent main line.
- Intake merge lands in `neoforge/main-clean` only after validation completes.

Stop conditions:
- If merge target is anything other than `neoforge/main-clean` for intake work, stop and correct merge plan.