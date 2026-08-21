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
