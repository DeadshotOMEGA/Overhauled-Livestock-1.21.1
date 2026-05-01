# Integration Intake Reporting

This directory contains repeatable intake-cycle reporting for Gate 4 and Gate 5.

## Naming Policy
- Primary cycle label: `fork-YYYY-MM-DD`

Cycle reports are `fork`-only to match the active integration branch convention.

## Structure
- `integration/intake-report.sh`: report generation entrypoint
- `integration/cycles/YYYY-MM-DD/`: per-cycle reports and artifacts
- `integration/forge-to-neoforge-1.21.1-cookbook.md`: safe bulk-change cookbook

Per-cycle files:
- `gate4-delta-report.md`
- `gate5-disposition-log.md`
- `gate5-validation-log.md`
- `artifacts/git-log.txt`
- `artifacts/git-diff-stat.txt`
- `artifacts/git-diff.patch`
- `artifacts/git-numstat.tsv`
- `artifacts/git-changed-files.txt`
- `artifacts/gate4-command-transcript.txt`

## Commands
```bash
# initialize cycle folder and baseline templates
./integration/intake-report.sh init --date YYYY-MM-DD

# generate authoritative Gate 4 delta package
./integration/intake-report.sh gate4 --date YYYY-MM-DD

# seed Gate 5 disposition + validation templates
./integration/intake-report.sh gate5-template --date YYYY-MM-DD
```

## Gate 4 Baseline Validation
Before Gate 4 output generation, the script validates:
- `baseline/fork-YYYY-MM-DD` (branch)
- `baseline/upstream-YYYY-MM-DD` (branch)
- `baseline-fork-YYYY-MM-DD` (tag)
- `baseline-upstream-YYYY-MM-DD` (tag)

If any baseline ref is missing, the command exits with a clear error and writes no Gate 4 outputs.

## Gate 5 Disposition Rules
Valid dispositions:
- `apply`
- `adapt`
- `skip/replaced`

Default tracking unit is one row per upstream commit. You can split into finer rows when a commit contains multiple independently-ported changes.

## Relationship to Migration Progress Log
This reporting system is intake-focused and does not replace `migration-progress-1.20.1-to-1.21.1.md`.
