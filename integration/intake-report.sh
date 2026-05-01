#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
INTEGRATION_DIR="$REPO_ROOT/integration"
CYCLES_DIR="$INTEGRATION_DIR/cycles"

usage() {
  cat <<'USAGE'
Usage:
  intake-report.sh init --date YYYY-MM-DD
  intake-report.sh gate4 --date YYYY-MM-DD
  intake-report.sh gate5-template --date YYYY-MM-DD
USAGE
}

die() {
  echo "ERROR: $*" >&2
  exit 1
}

require_date() {
  if [[ -z "${DATE:-}" ]]; then
    die "missing --date YYYY-MM-DD"
  fi
  if [[ ! "$DATE" =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
    die "invalid date '$DATE' (expected YYYY-MM-DD)"
  fi
}

parse_args() {
  DATE=""
  while [[ $# -gt 0 ]]; do
    case "$1" in
      --date)
        shift
        [[ $# -gt 0 ]] || die "--date requires a value"
        DATE="$1"
        shift
        ;;
      -h|--help)
        usage
        exit 0
        ;;
      *)
        die "unknown argument: $1"
        ;;
    esac
  done
}

set_cycle_vars() {
  PRIMARY_LABEL="fork-$DATE"
  CYCLE_DIR="$CYCLES_DIR/$DATE"
  ARTIFACTS_DIR="$CYCLE_DIR/artifacts"

  FORK_BASELINE_BRANCH="baseline/fork-$DATE"
  UPSTREAM_BASELINE_BRANCH="baseline/upstream-$DATE"
  FORK_BASELINE_TAG="baseline-fork-$DATE"
  UPSTREAM_BASELINE_TAG="baseline-upstream-$DATE"
  RANGE_DOTS="$FORK_BASELINE_BRANCH..$UPSTREAM_BASELINE_BRANCH"
  RANGE_TRIPLEDOTS="$FORK_BASELINE_BRANCH...$UPSTREAM_BASELINE_BRANCH"
}

ensure_cycle_dir() {
  mkdir -p "$ARTIFACTS_DIR"
}

validate_baseline_refs() {
  local missing=()

  git show-ref --verify --quiet "refs/heads/$FORK_BASELINE_BRANCH" || missing+=("branch:$FORK_BASELINE_BRANCH")
  git show-ref --verify --quiet "refs/heads/$UPSTREAM_BASELINE_BRANCH" || missing+=("branch:$UPSTREAM_BASELINE_BRANCH")
  git show-ref --verify --quiet "refs/tags/$FORK_BASELINE_TAG" || missing+=("tag:$FORK_BASELINE_TAG")
  git show-ref --verify --quiet "refs/tags/$UPSTREAM_BASELINE_TAG" || missing+=("tag:$UPSTREAM_BASELINE_TAG")

  if (( ${#missing[@]} > 0 )); then
    {
      echo "Missing baseline references for $DATE:"
      for ref in "${missing[@]}"; do
        echo "  - $ref"
      done
      echo "Create/fetch missing refs before running Gate 4 or Gate 5 templating."
    } >&2
    exit 1
  fi
}

write_init_gate4_template() {
  local report_file="$CYCLE_DIR/gate4-delta-report.md"

  if [[ -f "$report_file" ]]; then
    return
  fi

  cat > "$report_file" <<GATE4_TEMPLATE
# Gate 4 Delta Report ($DATE)

- Primary cycle label: \`$PRIMARY_LABEL\`
- Status: \`NOT_GENERATED\`

## Purpose
Authoritative upstream delta package derived from frozen baselines.

## Baseline References
- Fork baseline branch: \`$FORK_BASELINE_BRANCH\`
- Upstream baseline branch: \`$UPSTREAM_BASELINE_BRANCH\`
- Fork baseline tag: \`$FORK_BASELINE_TAG\`
- Upstream baseline tag: \`$UPSTREAM_BASELINE_TAG\`

## Artifacts
- [git-log.txt](./artifacts/git-log.txt)
- [git-diff-stat.txt](./artifacts/git-diff-stat.txt)
- [git-diff.patch](./artifacts/git-diff.patch)
- [git-numstat.tsv](./artifacts/git-numstat.tsv)
- [git-changed-files.txt](./artifacts/git-changed-files.txt)
- [gate4-command-transcript.txt](./artifacts/gate4-command-transcript.txt)

Run \`./integration/intake-report.sh gate4 --date $DATE\` to populate metrics and summaries.
GATE4_TEMPLATE
}

write_init_gate5_disposition_template() {
  local disposition_file="$CYCLE_DIR/gate5-disposition-log.md"

  if [[ -f "$disposition_file" ]]; then
    return
  fi

  cat > "$disposition_file" <<GATE5_DISP_TEMPLATE
# Gate 5 Disposition Log ($DATE)

- Primary cycle label: \`$PRIMARY_LABEL\`
- Tracking unit default: upstream commit-level item

Dispositions:
- \`apply\`
- \`adapt\`
- \`skip/replaced\`

| item_id | upstream_ref | summary | disposition | target_paths | rationale | owner | status | notes |
|---|---|---|---|---|---|---|---|---|
| G5-001 | TBD | TBD | TBD | TBD | TBD | TBD | NOT_STARTED | |
GATE5_DISP_TEMPLATE
}

write_init_gate5_validation_template() {
  local validation_file="$CYCLE_DIR/gate5-validation-log.md"

  if [[ -f "$validation_file" ]]; then
    return
  fi

  cat > "$validation_file" <<GATE5_VALID_TEMPLATE
# Gate 5 Validation Log ($DATE)

- Primary cycle label: \`$PRIMARY_LABEL\`

## Environment Snapshot
- Branch: \`$(git branch --show-current)\`
- Commit: \`$(git rev-parse --short HEAD)\`
- Java version: \`$(java -version 2>&1 | head -n 1 || echo unknown)\`
- Gradle version: \`$(./gradlew --version 2>/dev/null | awk '/^Gradle / {print $2; exit}' || echo unknown)\`
- Snapshot time: \`$(date -Iseconds)\`

## Validation Commands Run
| command | result | timestamp | notes |
|---|---|---|---|
| \`./gradlew compileJava --no-daemon\` | TBD | TBD | |
| \`./gradlew processResources --no-daemon\` | TBD | TBD | |
| \`./gradlew runData --no-daemon\` | TBD | TBD | |

## Manual Checks
- [ ] Core gameplay parity checks completed (mounts, AI, inventory interactions)
- [ ] Key recipes and loot tables verified
- [ ] Datapack/tag loading verified
- [ ] Optional compat paths validated or explicitly deferred

## Merge Readiness Verdict
- Verdict: \`TBD\`
- Decision owner: \`TBD\`
- Decision time: \`TBD\`
- Blocking items: \`TBD\`
GATE5_VALID_TEMPLATE
}

init_cycle() {
  ensure_cycle_dir
  write_init_gate4_template
  write_init_gate5_disposition_template
  write_init_gate5_validation_template

  echo "Initialized cycle folder: $CYCLE_DIR"
}

generate_gate4() {
  ensure_cycle_dir
  validate_baseline_refs

  local log_file="$ARTIFACTS_DIR/git-log.txt"
  local stat_file="$ARTIFACTS_DIR/git-diff-stat.txt"
  local diff_file="$ARTIFACTS_DIR/git-diff.patch"
  local numstat_file="$ARTIFACTS_DIR/git-numstat.tsv"
  local changed_files_file="$ARTIFACTS_DIR/git-changed-files.txt"
  local transcript_file="$ARTIFACTS_DIR/gate4-command-transcript.txt"
  local top_churn_file="$ARTIFACTS_DIR/top-churn.tsv"
  local report_file="$CYCLE_DIR/gate4-delta-report.md"

  git log --oneline "$RANGE_DOTS" > "$log_file"
  git diff --stat "$RANGE_TRIPLEDOTS" > "$stat_file"
  git diff "$RANGE_TRIPLEDOTS" > "$diff_file"
  git diff --numstat "$RANGE_TRIPLEDOTS" > "$numstat_file"
  git diff --name-only "$RANGE_TRIPLEDOTS" > "$changed_files_file"

  {
    echo "# Gate 4 Command Transcript"
    echo "generated_at: $(date -Iseconds)"
    echo "date: $DATE"
    echo "primary_label: $PRIMARY_LABEL"
    echo
    echo "command: git log --oneline $RANGE_DOTS"
    git log --oneline "$RANGE_DOTS"
    echo
    echo "command: git diff --stat $RANGE_TRIPLEDOTS"
    git diff --stat "$RANGE_TRIPLEDOTS"
  } > "$transcript_file"

  local commit_count files_changed additions deletions
  commit_count="$(git rev-list --count "$RANGE_DOTS")"
  files_changed="$(wc -l < "$changed_files_file" | tr -d ' ')"
  additions="$(awk '{a = ($1 == "-" ? 0 : $1); add += a} END {print add + 0}' "$numstat_file")"
  deletions="$(awk '{d = ($2 == "-" ? 0 : $2); del += d} END {print del + 0}' "$numstat_file")"

  awk 'BEGIN {OFS="\t"} {a = ($1 == "-" ? 0 : $1); d = ($2 == "-" ? 0 : $2); t = a + d; print t, a, d, $3}' "$numstat_file" \
    | sort -nr \
    | head -n 10 > "$top_churn_file"

  cat > "$report_file" <<GATE4_REPORT
# Gate 4 Delta Report ($DATE)

- Primary cycle label: \`$PRIMARY_LABEL\`
- Generated at: \`$(date -Iseconds)\`
- Status: \`GENERATED\`

## Baseline References
- Fork baseline branch: \`$FORK_BASELINE_BRANCH\`
- Upstream baseline branch: \`$UPSTREAM_BASELINE_BRANCH\`
- Fork baseline tag: \`$FORK_BASELINE_TAG\`
- Upstream baseline tag: \`$UPSTREAM_BASELINE_TAG\`

## Delta Metrics
| metric | value |
|---|---:|
| commit_count | $commit_count |
| files_changed | $files_changed |
| lines_added | $additions |
| lines_deleted | $deletions |

## Upstream Commits
\`\`\`text
$(cat "$log_file")
\`\`\`

## Top File Churn
| total | added | deleted | path |
|---:|---:|---:|---|
GATE4_REPORT

  if [[ -s "$top_churn_file" ]]; then
    while IFS=$'\t' read -r total added deleted path; do
      printf '| %s | %s | %s | `%s` |\n' "$total" "$added" "$deleted" "$path" >> "$report_file"
    done < "$top_churn_file"
  else
    echo "| 0 | 0 | 0 | \`(no changed files)\` |" >> "$report_file"
  fi

  cat >> "$report_file" <<GATE4_ARTIFACTS

## Artifacts
- [git-log.txt](./artifacts/git-log.txt)
- [git-diff-stat.txt](./artifacts/git-diff-stat.txt)
- [git-diff.patch](./artifacts/git-diff.patch)
- [git-numstat.tsv](./artifacts/git-numstat.tsv)
- [git-changed-files.txt](./artifacts/git-changed-files.txt)
- [gate4-command-transcript.txt](./artifacts/gate4-command-transcript.txt)
GATE4_ARTIFACTS

  echo "Generated Gate 4 report and artifacts in: $CYCLE_DIR"
}

seed_gate5_templates() {
  ensure_cycle_dir
  validate_baseline_refs

  local disposition_file="$CYCLE_DIR/gate5-disposition-log.md"
  local validation_file="$CYCLE_DIR/gate5-validation-log.md"
  local mapfile_status=0

  mapfile -t commit_lines < <(git log --oneline "$RANGE_DOTS") || mapfile_status=$?
  if [[ "$mapfile_status" -ne 0 ]]; then
    die "failed to read commit list for Gate 5 template"
  fi

  cat > "$disposition_file" <<GATE5_DISP
# Gate 5 Disposition Log ($DATE)

- Primary cycle label: \`$PRIMARY_LABEL\`
- Tracking unit default: upstream commit-level item
- Generated at: \`$(date -Iseconds)\`

Dispositions:
- \`apply\`
- \`adapt\`
- \`skip/replaced\`

| item_id | upstream_ref | summary | disposition | target_paths | rationale | owner | status | notes |
|---|---|---|---|---|---|---|---|---|
GATE5_DISP

  if [[ "${#commit_lines[@]}" -eq 0 ]]; then
    echo "| G5-001 | \`(none)\` | No upstream commits in this range | TBD | TBD | TBD | TBD | NOT_STARTED | |" >> "$disposition_file"
  else
    local idx=1
    local line hash summary item_id
    for line in "${commit_lines[@]}"; do
      hash="${line%% *}"
      summary="${line#* }"
      if [[ "$summary" == "$line" ]]; then
        summary="(no summary)"
      fi
      summary="${summary//|/\\|}"
      item_id="$(printf 'G5-%03d' "$idx")"
      printf '| %s | `%s` | %s | TBD | TBD | TBD | TBD | NOT_STARTED | |\n' "$item_id" "$hash" "$summary" >> "$disposition_file"
      idx=$((idx + 1))
    done
  fi

  cat > "$validation_file" <<GATE5_VALID
# Gate 5 Validation Log ($DATE)

- Primary cycle label: \`$PRIMARY_LABEL\`
- Generated at: \`$(date -Iseconds)\`

## Environment Snapshot
- Branch: \`$(git branch --show-current)\`
- Commit: \`$(git rev-parse --short HEAD)\`
- Java version: \`$(java -version 2>&1 | head -n 1 || echo unknown)\`
- Gradle version: \`$(./gradlew --version 2>/dev/null | awk '/^Gradle / {print $2; exit}' || echo unknown)\`

## Validation Commands Run
| command | result | timestamp | notes |
|---|---|---|---|
| \`./gradlew compileJava --no-daemon\` | TBD | TBD | |
| \`./gradlew processResources --no-daemon\` | TBD | TBD | |
| \`./gradlew runData --no-daemon\` | TBD | TBD | |
| \`./gradlew runServer --no-daemon\` | TBD | TBD | optional smoke |

## Manual Checks
- [ ] Core gameplay parity checks completed
- [ ] Datapack/tag loading clean
- [ ] Key recipe and loot changes verified
- [ ] Optional compatibility modules validated or deferred with rationale

## Merge Readiness Verdict
- Verdict: \`TBD\`
- Decision owner: \`TBD\`
- Decision time: \`TBD\`
- Blocking items: \`TBD\`
GATE5_VALID

  echo "Generated Gate 5 disposition and validation templates in: $CYCLE_DIR"
}

main() {
  [[ $# -gt 0 ]] || { usage; exit 1; }

  local command="$1"
  shift

  case "$command" in
    init|gate4|gate5-template)
      parse_args "$@"
      require_date
      set_cycle_vars
      ;;
    -h|--help)
      usage
      exit 0
      ;;
    *)
      die "unknown subcommand: $command"
      ;;
  esac

  case "$command" in
    init)
      init_cycle
      ;;
    gate4)
      validate_baseline_refs
      init_cycle
      generate_gate4
      ;;
    gate5-template)
      validate_baseline_refs
      init_cycle
      seed_gate5_templates
      ;;
  esac
}

main "$@"
