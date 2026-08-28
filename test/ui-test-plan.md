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
deadline /by 2019-12-08
deadline return book /by 2019-12-08
event project meeting /from /to 2019-12-02 1600
event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600
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
  [D][ ] return book (by: Dec 8 2019)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: The /from part of an event cannot be empty.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: I couldn't find that task number.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Dec 8 2019)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][X] return book (by: Dec 8 2019)
3.[E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Find tasks by keyword

Aim: Verify that find lists matching task descriptions, handles no matches, and rejects a missing keyword.

```input
todo read book
deadline return book /by 2019-12-08
todo buy milk
mark 1
mark 2
find book
find chocolate
find
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
Got it. I've added this task:
  [D][ ] return book (by: Dec 8 2019)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] buy milk
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Dec 8 2019)
____________________________________________________________
____________________________________________________________
Here are the matching tasks in your list:
1.[T][X] read book
2.[D][X] return book (by: Dec 8 2019)
____________________________________________________________
____________________________________________________________
Here are the matching tasks in your list:
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Please provide a keyword after find.
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Invalid fields do not affect status updates

Aim: Verify that empty date fields and invalid unmark numbers do not change existing task statuses.

```input
deadline write report /by 2019-12-06
deadline submit report /by
event workshop /from 2019-12-02 0900 /to
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
  [D][ ] write report (by: Dec 6 2019)
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
  [D][X] write report (by: Dec 6 2019)
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Task numbers should be whole numbers.
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [D][ ] write report (by: Dec 6 2019)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] write report (by: Dec 6 2019)
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
deadline /by 2019-12-08
deadline return book
event project meeting /from 2019-12-02 1400
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

## Test Case: Delete task and renumber list

Aim: Verify that deleting a task removes it, renumbers later tasks, and rejects invalid delete numbers.

```input
todo read book
deadline return book /by 2019-12-08
event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600
delete 2
list
delete 5
delete abc
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
Got it. I've added this task:
  [D][ ] return book (by: Dec 8 2019)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [D][ ] return book (by: Dec 8 2019)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: I couldn't find that task number.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Task numbers should be whole numbers.
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Load saved tasks

Aim: Verify that tasks saved in data/bobby.txt are loaded when the chatbot starts.

```data
T | 1 | read book
D | 0 | return book | 2019-12-08T00:00
E | 0 | project meeting | 2019-12-02T14:00 | 2019-12-02T16:00
```

```input
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
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: Dec 8 2019)
3.[E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

## Test Case: Save task changes

Aim: Verify that adding, marking, and deleting tasks update data/bobby.txt.

```input
todo alpha
deadline beta /by 2019-12-06
event gamma /from 2019-12-02 1300 /to 2019-12-02 1400
mark 2
delete 1
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
  [T][ ] alpha
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] beta (by: Dec 6 2019)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] gamma (from: Dec 2 2019, 1:00pm to: Dec 2 2019, 2:00pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] beta (by: Dec 6 2019)
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [T][ ] alpha
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

```saved
D | 1 | beta | 2019-12-06T00:00
E | 0 | gamma | 2019-12-02T13:00 | 2019-12-02T14:00
```

## Test Case: Parse deadline date and time

Aim: Verify that slash-format date-time input is parsed, displayed clearly, and saved in ISO format.

```input
deadline return book /by 2/12/2019 1800
deadline bad date /by no idea
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
  [D][ ] return book (by: Dec 2 2019, 6:00pm)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bobby needs a clearer command: Please use a date format like 2019-12-02 or 2/12/2019 1800.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] return book (by: Dec 2 2019, 6:00pm)
____________________________________________________________
____________________________________________________________
Goodbye! Bobby signing out...
____________________________________________________________
```

```saved
D | 0 | return book | 2019-12-02T18:00
```
