# AGENTS Workflow Spec for Branch Governance

This file is the canonical runbook for Codex sessions in this repository.  
Primary goal: repeatable, safe branch governance with no process re-decisions per session.

## Companion Runbooks

- [AGENTS.intake.md](AGENTS.intake.md): operational start-gates for each upstream intake cycle before and during integration-branch setup.

## 1) Branch Roles (Hard Rules)

- `master`: upstream mirror lane only. No feature work. Sync-only operations.
- `baseline/*` branches and `baseline-*` tags: frozen intake snapshots. Never edited after creation.
- `neoforge/main-clean`: clean upstream-equivalent NeoForge line. No personal feature commits.
- `integration/fork-YYYY-MM-DD`: required working branch for each upstream intake cycle.
- `game/main`: default base branch for custom gameplay and visual changes.
- `feature/*`, `fix/*`, `build/*`: temporary task branches. Merge back into `game/main` unless the task is explicitly upstream-port work.

## 2) Protected-Branch Guardrails (Mandatory)

- Never commit directly to `master`.
- Never commit directly to `neoforge/main-clean`.
- Before making any file edits, confirm current branch with `git branch --show-current`.
- If current branch is `master` or `neoforge/main-clean`, stop and switch to a valid work branch before editing.
- Before any destructive git command (examples: `reset --hard`, `rebase`, `clean -fd`, force-push, branch/tag delete), stop and ask for explicit user confirmation.

## 3) Upstream Intake Playbook (Decision-Complete, Dated Cycles)

Use this flow for every upstream intake cycle. Date format is always `YYYY-MM-DD`.

1. Freeze the cycle with dated baselines:
   - `baseline/fork-YYYY-MM-DD`
   - `baseline/upstream-YYYY-MM-DD`
   - Matching tags:
     - `baseline-fork-YYYY-MM-DD`
     - `baseline-upstream-YYYY-MM-DD`
2. Generate upstream delta from the frozen baselines:
   - Compare `baseline/fork-YYYY-MM-DD` -> `baseline/upstream-YYYY-MM-DD`.
3. Port changes on `integration/fork-YYYY-MM-DD` against NeoForge code:
   - Base this integration branch from `neoforge/main-clean`.
   - Apply upstream delta change-by-change.
4. Track disposition for each upstream change as one of:
   - `apply`
   - `adapt`
   - `skip/replaced`
5. Validate the integration branch.
6. Merge validated intake:
   - `integration/fork-YYYY-MM-DD` -> `neoforge/main-clean`
7. Optional promotion for gameplay lane:
   - `neoforge/main-clean` -> `game/main` (only when ready to advance the gameplay base).

## 4) Merge Direction Rules

- Upstream intake lane: `integration/fork-YYYY-MM-DD` -> `neoforge/main-clean`.
- Gameplay/custom lane: `feature/*|fix/*|build/*` -> `game/main`.
- Optional upstream-port task branches: merge target must be explicitly chosen before work starts; default target is `neoforge/main-clean` if the work is upstream-equivalent.
- `integration/fork-YYYY-MM-DD` is temporary working state only; once validated, merge into `neoforge/main-clean`, then optionally promote `neoforge/main-clean` into `game/main` when gameplay base advancement is desired.

## 5) Compact Command Checklist

### A) Branch Setup / Safety

```bash
git fetch --all --prune
git branch --show-current
git status
```

### B) Intake Baseline Freeze

```bash
DATE=YYYY-MM-DD

# from current fork baseline commit
git switch -c baseline/fork-$DATE
git tag baseline-fork-$DATE

# from upstream baseline commit
git switch -c baseline/upstream-$DATE
git tag baseline-upstream-$DATE
```

### C) Intake Branch Creation

```bash
DATE=YYYY-MM-DD
git switch neoforge/main-clean
git pull --ff-only
git switch -c integration/fork-$DATE
```

### D) Diff Generation (Frozen Baselines)

```bash
DATE=YYYY-MM-DD
git log --oneline baseline/fork-$DATE..baseline/upstream-$DATE
git diff --stat baseline/fork-$DATE...baseline/upstream-$DATE
git diff baseline/fork-$DATE...baseline/upstream-$DATE
```

### E) Merge Targets (Explicit Source -> Destination)

```bash
# validated intake -> clean upstream-equivalent lane
git switch neoforge/main-clean
git merge --no-ff integration/fork-$DATE

# feature/fix/build work -> gameplay lane
git switch game/main
git merge --no-ff feature/<name>   # or fix/<name>, build/<name>
```

### F) Final Verification

```bash
git branch --show-current
git status
git branch -vv
```

Verify:
- Branch is correct for the intended lane.
- Worktree is clean or intentionally staged.
- Upstream tracking is sane before push.
