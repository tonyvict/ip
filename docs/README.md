# Tommy User Guide

Tommy is a keyboard-first task manager with tagging, search, and a built-in Help window.  
Commands are **case-sensitive** and task numbering starts at **1**.

---

## 📝 Add Tasks

### Todo
- **What:** Simple task without time info
- **Command:** `todo <description>`
- **Example:** `todo Buy groceries`
- **Output:** `Added: [T][ ] Buy groceries`

### Deadline
- **What:** Task due by a specific date/time
- **Command:** `deadline <description> /by <YYYY-MM-DD HHMM>`
- **Example:** `deadline Submit report /by 2024-12-31 2359`
- **Output:** `Added: [D][ ] Submit report (by: 2024-12-31 23:59)`

### Event
- **What:** Task spanning a start and end time
- **Command:** `event <description> /from <start> /to <end>`
- **Example:** `event Team meeting /from 1400 /to 1530`
- **Output:** `Added: [E][ ] Team meeting (from: 14:00 to: 15:30)`

---

## 📊 Task Status

### Mark / Unmark
- **What:** Toggle completion state of a task
- **Commands:**  
  - `mark <index>` → mark done  
  - `unmark <index>` → mark undone
- **Example:** `mark 2`
- **Output:** `Nice! I've marked this task as done: [D][X] ...`

---

## 🧹 Task Management

### Delete
- **What:** Remove a task by index
- **Command:** `delete <index>`
- **Example:** `delete 1`
- **Output:** `Noted. I've removed this task: ...`

### List
- **What:** Show all tasks with indices and tags
- **Command:** `list`
- **Output (snippet):**


---

## 🏷️ Tagging

### Add / Remove Tag
- **What:** Attach or remove a single tag from a task (multiple tags supported)
- **Commands:**  
- `tag <index> <tag>`  
- `untag <index> <tag>`
- **Example:** `tag 1 urgent`
- **Output:** `Tagged task 1 with #urgent`

---

## 🔎 Search

### Find
- **What:** Search tasks by keyword (matches description and tags)
- **Command:** `find <keyword>`
- **Example:** `find groceries`
- **Output (snippet):**

