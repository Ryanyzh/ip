#!/usr/bin/env python3
"""Run console UI tests recorded in test/ui-test-plan.md."""
from __future__ import annotations

import argparse
import re
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path


DEFAULT_PLAN = Path("test/ui-test-plan.md")
DEFAULT_CLASSES_DIR = Path("/tmp/ip-ui-test-classes")
CASE_HEADING_RE = re.compile(r"^## Test Case:\s*(.+?)\s*$", re.MULTILINE)
FENCE_RE = re.compile(r"```(input|expected)\n(.*?)```", re.DOTALL)


@dataclass
class UiTestCase:
    name: str
    aim: str
    console_input: str
    expected_output: str


def normalize_output(text: str) -> str:
    return text.replace("\r\n", "\n").replace("\r", "\n")


def parse_test_plan(plan_path: Path) -> list[UiTestCase]:
    text = plan_path.read_text(encoding="utf-8")
    matches = list(CASE_HEADING_RE.finditer(text))
    cases: list[UiTestCase] = []

    for index, match in enumerate(matches):
        name = match.group(1)
        start = match.end()
        end = matches[index + 1].start() if index + 1 < len(matches) else len(text)
        section = text[start:end]
        aim = parse_aim(section)
        fences = {kind: body for kind, body in FENCE_RE.findall(section)}

        if "input" not in fences or "expected" not in fences:
            raise ValueError(f"{name}: missing ```input``` or ```expected``` block")

        cases.append(UiTestCase(
            name=name,
            aim=aim,
            console_input=normalize_output(fences["input"]),
            expected_output=normalize_output(fences["expected"]),
        ))

    if not cases:
        raise ValueError(f"No test cases found in {plan_path}")

    return cases


def parse_aim(section: str) -> str:
    for line in section.splitlines():
        stripped = line.strip()
        if stripped.startswith("Aim:"):
            return stripped.removeprefix("Aim:").strip()
    return ""


def compile_project(repo: Path, classes_dir: Path) -> None:
    classes_dir.mkdir(parents=True, exist_ok=True)
    java_files = sorted((repo / "src/main/java").glob("*.java"))
    if not java_files:
        raise ValueError("No Java files found in src/main/java")

    command = ["javac", "-d", str(classes_dir), *[str(path) for path in java_files]]
    result = subprocess.run(command, cwd=repo, capture_output=True, text=True)
    if result.returncode != 0:
        print("Compilation failed.", file=sys.stderr)
        print(result.stderr, file=sys.stderr)
        sys.exit(result.returncode)


def run_case(repo: Path, classes_dir: Path, test_case: UiTestCase) -> str:
    console_input = test_case.console_input
    if console_input and not console_input.endswith("\n"):
        console_input += "\n"

    result = subprocess.run(
        ["java", "-cp", str(classes_dir), "Bobby"],
        cwd=repo,
        input=console_input,
        capture_output=True,
        text=True,
    )
    if result.returncode != 0:
        raise RuntimeError(result.stderr)
    return normalize_output(result.stdout)


def print_transcript(test_case: UiTestCase, actual_output: str) -> None:
    print(f"## {test_case.name}")
    if test_case.aim:
        print(f"Aim: {test_case.aim}")
    print()
    print("Console input:")
    print("```")
    print(test_case.console_input, end="" if test_case.console_input.endswith("\n") else "\n")
    print("```")
    print()
    print("Console output:")
    print("```")
    print(actual_output, end="" if actual_output.endswith("\n") else "\n")
    print("```")
    print()


def report_failure(test_case: UiTestCase, expected: str, actual: str) -> None:
    print(f"FAILED: {test_case.name}")
    if test_case.aim:
        print(f"Aim: {test_case.aim}")
    print()
    print("Console input:")
    print("```")
    print(test_case.console_input, end="" if test_case.console_input.endswith("\n") else "\n")
    print("```")
    print()
    print("Expected output:")
    print("```")
    print(expected, end="" if expected.endswith("\n") else "\n")
    print("```")
    print()
    print("Actual output:")
    print("```")
    print(actual, end="" if actual.endswith("\n") else "\n")
    print("```")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--plan", type=Path, default=DEFAULT_PLAN)
    parser.add_argument("--classes-dir", type=Path, default=DEFAULT_CLASSES_DIR)
    args = parser.parse_args()

    repo = Path.cwd()
    cases = parse_test_plan(args.plan)
    compile_project(repo, args.classes_dir)

    for test_case in cases:
        actual = run_case(repo, args.classes_dir, test_case)
        expected = test_case.expected_output
        if actual != expected:
            report_failure(test_case, expected, actual)
            return 1
        print_transcript(test_case, actual)

    print(f"PASS: {len(cases)} test case(s)")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
