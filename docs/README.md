# Leo User Guide

Leo is a task management chatbot that helps users keep track of todos, deadlines, and events. With a simple command-line
interface, Leo makes it easy to organize your tasks and stay productive. Your tasks data is saved locally.

![Ui](Ui.png)

## Getting Started

1. Ensure you have Java 17 or later installed on your computer
2. Download the `leo.jar` file from the latest release
3. Run Leo using the following command:
   ```bash
   java -jar leo.jar
   ```
4. Start typing commands to manage your tasks!

## Features

### 1. Adding Tasks

#### A) `todo` - Add a simple todo task

Adds a basic task without any specific deadline or time constraints.

**Format:** `todo <description>`

**Example:**

```
todo read book
```

**Expected outcome:**

```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task in the list.
```

#### B) `deadline` - Add a task with a deadline

Creates a task that must be completed by a specific date.

**Format:** `deadline <description> /by <date>`

**Date format:** `yyyy-MM-dd` (e.g., 2024-12-25)

**Example:**

```
deadline submit assignment /by 2024-03-15
```

**Expected outcome:**

```
Got it. I've added this task:
  [D][ ] submit assignment (by: Mar 15 2024)
Now you have 2 tasks in the list.
```

#### C) `event` - Add an event with start and end times

Creates a task for an event that occurs over a specific time period.

**Format:** `event <description> /from <start> /to <end>`

**Example:**

```
event team meeting /from 2pm /to 3pm
```

**Expected outcome:**

```
Got it. I've added this task:
  [E][ ] team meeting (from: 2pm to: 3pm)
Now you have 3 tasks in the list.
```

### 2. Managing Tasks

#### A) `list` - View all tasks

Displays all tasks in your task list with their current status.

**Format:** `list`

**Expected outcome:**

```
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] submit assignment (by: Mar 15 2024)
3. [E][ ] team meeting (from: 2pm to: 3pm)
```

#### B) `find` - Search for tasks

Searches through your tasks to find those containing a specific keyword.

**Format:** `find <keyword>`

**Example:**

```
find meeting
```

**Expected outcome:**

```
Here are the matching tasks in your list:
1. [E][ ] team meeting (from: 2pm to: 3pm)
```

#### C) `mark` - Mark a task as done

Marks a specific task as completed.

**Format:** `mark <task_number>`

**Example:**

```
mark 1
```

**Expected outcome:**

```
Nice! I've marked this task as done:
  [T][X] read book
```

#### D) `unmark` - Mark a task as not done

Marks a previously completed task as incomplete.

**Format:** `unmark <task_number>`

**Example:**

```
unmark 1
```

**Expected outcome:**

```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

#### E) `delete` - Remove a task

Permanently removes a task from your list.

**Format:** `delete <task_number>`

**Example:**

```
delete 2
```

**Expected outcome:**

```
Noted. I've removed this task:
  [D][ ] submit assignment (by: Mar 15 2024)
Now you have 2 tasks in the list.
```

### 3. Getting Help

#### A) `help` - Show available commands

Displays a list of all available commands and their usage.

**Format:** `help` or `h`

**Expected outcome:**

```
Available commands:
- todo <description>
- deadline <description> /by <date>
- event <description> /from <start> /to <end>
- list
- find <keyword>
- mark <task_number>
- unmark <task_number>
- delete <task_number>
- help
- bye
```

#### B) `bye` - Exit the application

Saves your tasks and closes Leo.

**Format:** `bye`

**Expected outcome:**

```
Bye. Hope to see you again soon!
```

## FAQ & Notes

**Error Handling:** Leo handles invalid inputs gracefully and provides helpful error messages. For example:

- Missing task descriptions will prompt you to provide them
- Invalid date formats will remind you to use `yyyy-MM-dd`
- Invalid task numbers will inform you that the task doesn't exist

**Data Persistence:** Your tasks are automatically saved to a local file (`data/tasks.txt`) and will be restored when
you restart Leo.

**Task Numbering:** Task numbers start from 1 and correspond to the order they appear in the `list` command. Use these
numbers with `mark`, `unmark`, and `delete` commands.
