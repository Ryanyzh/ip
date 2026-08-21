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
