# Bobby User Guide

Bobby is a desktop chatbot for tracking todos, deadlines, events, and tags.
It is designed for users who prefer typing quick commands while still using a
friendly GUI.

![Bobby GUI screenshot](Ui.png)

## Quick Start

1. Ensure that Java 25 is installed.
1. Open a terminal in the project root.
1. Run Bobby:

   ```bash
   ./gradlew run
   ```

1. Type a command in the input box and press <kbd>Enter</kbd>, or click
   **Send**.
1. Try these commands:
   - `todo read CS2103 notes`
   - `deadline submit iP /by 2026-09-20 2359`
   - `event team meeting /from 2026-09-18 1400 /to 2026-09-18 1500`
   - `list`

## Features

### Notes About Command Formats

- Words in `UPPER_CASE` are values you provide.
  For example, in `todo DESCRIPTION`, replace `DESCRIPTION` with
  `read CS2103 notes`.
- Task numbers refer to the numbers shown by the `list` command.
- Dates can be written as `yyyy-MM-dd`, `yyyy-MM-dd HHmm`, or
  `d/M/yyyy HHmm`.
- Descriptions cannot be empty and cannot contain `|`.

### Adding a Todo: `todo`

Adds a task without a date or time.

Format: `todo DESCRIPTION`

Examples:

- `todo read CS2103 notes`
- `todo borrow book`

### Adding a Deadline: `deadline`

Adds a task that must be completed by a date or time.

Format: `deadline DESCRIPTION /by DATE`

Examples:

- `deadline submit iP /by 2026-09-20`
- `deadline submit iP /by 2026-09-20 2359`
- `deadline submit iP /by 20/9/2026 2359`

### Adding an Event: `event`

Adds a task that happens between a start and end time.

Format: `event DESCRIPTION /from START /to END`

Example:

- `event team meeting /from 2026-09-18 1400 /to 2026-09-18 1500`

The start time must be before the end time.

### Listing Tasks: `list`

Shows all saved tasks.

Format: `list`

Example output:

```text
These are the stones upon your path:
1.[T][ ] read CS2103 notes
2.[D][ ] submit iP (by: Sep 20 2026, 11:59pm)
```

Task entries use these indicators:

- `[T]`: todo
- `[D]`: deadline
- `[E]`: event
- `[ ]`: not done
- `[X]`: done

### Marking a Task as Done: `mark`

Marks a task as completed.

Format: `mark TASK_NUMBER`

Example:

- `mark 1`

### Marking a Task as Not Done: `unmark`

Marks a completed task as not done.

Format: `unmark TASK_NUMBER`

Example:

- `unmark 1`

### Deleting a Task: `delete`

Removes a task from the list.

Format: `delete TASK_NUMBER`

Example:

- `delete 2`

After a task is deleted, the remaining tasks are renumbered.

### Tagging a Task: `tag`

Adds a tag to a task.

Format: `tag TASK_NUMBER #TAG`

Examples:

- `tag 1 #urgent`
- `tag 2 #school`

Tags must start with `#` and can contain letters, numbers, underscores, and
hyphens. Tags cannot contain spaces.

### Finding Tasks: `find`

Finds tasks whose descriptions or tags contain the keyword.

Format: `find KEYWORD`

Examples:

- `find iP`
- `find #urgent`

Search is case-insensitive.

### Exiting Bobby: `bye`

Closes Bobby.

Format: `bye`

## Error Handling

Bobby explains common mistakes and keeps running. For example:

- `todo` gives an error because the description is missing.
- `deadline report /by tomorrow` gives an error because the date format is
  invalid.
- `mark abc` gives an error because task numbers must be whole numbers.
- `delete 99` gives an error if there is no task with that number.

If `data/bobby.txt` is missing, Bobby starts with an empty task list. If the
file is corrupted, Bobby shows a loading error and still starts.

## Saving Data

Bobby saves tasks automatically after every command that changes the task list.
The data file is stored at:

```text
data/bobby.txt
```

You do not need to edit this file manually.

## Command Summary

| Action | Format | Example |
| --- | --- | --- |
| Add todo | `todo DESCRIPTION` | `todo borrow book` |
| Add deadline | `deadline DESCRIPTION /by DATE` | `deadline submit iP /by 2026-09-20 2359` |
| Add event | `event DESCRIPTION /from START /to END` | `event meeting /from 2026-09-18 1400 /to 2026-09-18 1500` |
| List tasks | `list` | `list` |
| Mark done | `mark TASK_NUMBER` | `mark 1` |
| Mark not done | `unmark TASK_NUMBER` | `unmark 1` |
| Delete task | `delete TASK_NUMBER` | `delete 2` |
| Add tag | `tag TASK_NUMBER #TAG` | `tag 1 #urgent` |
| Find tasks | `find KEYWORD` | `find book` |
| Exit | `bye` | `bye` |
