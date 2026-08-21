# UI Test Plan

Record console UI test cases here. Each test case must include an aim, an `input` block, and an `expected` block.

## Test Case: Add todo and list

Aim: Verify that a todo task is stored, listed, and followed by the bye message.

```input
todo borrow book
list
bye
```

```expected
____________________________________________________________
 ____        _     _           
| __ )  ___ | |__ | |__  _   _ 
|  _ \ / _ \| '_ \| '_ \| | | |
| |_) | (_) | |_) | |_) | |_| |
|____/ \___/|_.__/|_.__/ \__, |
                         |___/ 
Hello! I'm Bobby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Invalid additions do not change task list

Aim: Verify that invalid deadline, event, and mark commands do not add tasks or change valid task numbering.

```input
todo read book
deadline /by Sunday
deadline return book /by Sunday
event project meeting /from /to 4pm
event project meeting /from Mon 2pm /to 4pm
mark 5
mark 2
list
bye
```

```expected
____________________________________________________________
 ____        _     _           
| __ )  ___ | |__ | |__  _   _ 
|  _ \ / _ \| '_ \| '_ \| | | |
| |_) | (_) | |_) | |_) | |_| |
|____/ \___/|_.__/|_.__/ \__, |
                         |___/ 
Hello! I'm Bobby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The description of a deadline cannot be empty.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The /from part of an event cannot be empty.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: I couldn't find that task number.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][X] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Invalid fields do not affect status updates

Aim: Verify that empty date fields and invalid unmark numbers do not change existing task statuses.

```input
deadline write report /by Friday
deadline submit report /by
event workshop /from 9am /to
todo buy milk
mark 1
unmark two
unmark 1
list
bye
```

```expected
____________________________________________________________
 ____        _     _           
| __ )  ___ | |__ | |__  _   _ 
|  _ \ / _ \| '_ \| '_ \| | | |
| |_) | (_) | |_) | |_) | |_| |
|____/ \___/|_.__/|_.__/ \__, |
                         |___/ 
Hello! I'm Bobby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] write report (by: Friday)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The /by part of a deadline cannot be empty.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The /to part of an event cannot be empty.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] buy milk
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] write report (by: Friday)
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Task numbers should be whole numbers.
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [D][ ] write report (by: Friday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] write report (by: Friday)
2.[T][ ] buy milk
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Reject invalid commands

Aim: Verify that invalid commands produce clear error messages and the chatbot keeps running.

```input
todo
blah
deadline /by Sunday
deadline return book
event project meeting /from Mon 2pm
mark
mark abc
mark 1
bye
```

```expected
____________________________________________________________
 ____        _     _           
| __ )  ___ | |__ | |__  _   _ 
|  _ \ / _ \| '_ \| '_ \| | | |
| |_) | (_) | |_) | |_) | |_| |
|____/ \___/|_.__/|_.__/ \__, |
                         |___/ 
Hello! I'm Bobby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The description of a todo cannot be empty.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: I don't know what that means yet.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The description of a deadline cannot be empty.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Please tell me the deadline using /by.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Please tell me the event time using /from and /to.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Please provide a task number after mark.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Task numbers should be whole numbers.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: I couldn't find that task number.
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```
