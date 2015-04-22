# Introduction

This repo is meant as a tool to explore different programming concepts by implementing the game of Chess.  The project itself provides a very bare bones starting place.  You are encouraged to ask questions throughout this process; please don't be shy.

We assume you have at least passing knowledge of the game of chess, but no strategy is necessary for this project.  If you have little knowledge of chess, the Wikipedia article is an excellent reference:

http://en.wikipedia.org/wiki/Chess


# Getting Started With The App
The initial state of the project provides very little; not much more than a basic structure for displaying a chess board via a CLI.  After you fork the directory, you can run the program via Maven:

```Shell
$ mvn compile exec:java
{{ Maven output deleted for brevity }}
Welcome to Chess!
Type 'help' for a list of commands.

    a   b   c   d   e   f   g   h  
  +---+---+---+---+---+---+---+---+
8 | R | N | B | Q | K | B | N | R | 8
  +---+---+---+---+---+---+---+---+
7 | P | P | P | P | P | P | P | P | 7
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+---+---+---+---+
2 | p | p | p | p | p | p | p | p | 2
  +---+---+---+---+---+---+---+---+
1 | r | n | b | q | k | b | n | r | 1
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h  

White's Move
> help
Possible commands:
    'help'                       Show this menu
    'quit'                       Quit Chess
    'new'                        Create a new game
    'board'                      Show the chess board
    'list'                       List all possible moves
    'move <colrow> <colrow>'     Make a move
{{ Board redisplayed }}
> quit
Goodbye!
```

# Your Goals
The overall goal is to build the game of chess so that you can play it from the CLI against another person.  Specific goals are listed below, but please ask any questions that come to mind.

## Goal #1: List all the possible moves on the board
Each piece can move in specific ways.  In the CLI, you may have noticed a command, 'list', which is not currently implemented.  Your goal in this step is to implement that command, showing all the moves that the current player can make.

For the purposes of this exercise, you can specifically *ignore* these more complex moves:
- Castling the King
- Pawn En passant
- Pawn Promotion

After you're done, the 'list' should output all the possible moves.  For instance:

```Shell
White's Move
> list
White's Possible Moves:
    f2 f4
    f2 f3
    b1 a3
    b1 c3
    d2 d4
    d2 d3
    {{ ... other moves removed for brevity }}
    a   b   c   d   e   f   g   h
  +---+---+---+---+---+---+---+---+
8 | R | N | B | Q | K | B | N | R | 8
  +---+---+---+---+---+---+---+---+
7 | P | P | P | P | P | P | P | P | 7
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+---+---+---+---+
2 | p | p | p | p | p | p | p | p | 2
  +---+---+---+---+---+---+---+---+
1 | r | n | b | q | k | b | n | r | 1
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

White's Move
>
```

## Goal #2:  Implement the ability for White and Black to make moves via the CLI
When a new game is launched, the CLI prompts the White player to enter their first move.  However, the 'move' command is not currently implemented.  Your goal in this step is to implement the 'move' command.

Note that, traditionally, chess has a variety of ways to indicate moves.  For this exercise, we recommend you implement a system that represents a move as "move {from} {to}".

When you are finished, you should be able to issue a _move_ command and have it reflected in the CLI:

```Shell
...
White's Move
> move d2 d4
    a   b   c   d   e   f   g   h
  +---+---+---+---+---+---+---+---+
8 | R | N | B | Q | K | B | N | R | 8
  +---+---+---+---+---+---+---+---+
7 | P | P | P | P | P | P | P | P | 7
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   |   |   |   |   | 5
  +---+---+---+---+---+---+---+---+
4 |   |   |   | p |   |   |   |   | 4
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   | 3
  +---+---+---+---+---+---+---+---+
2 | p | p | p |   | p | p | p | p | 2
  +---+---+---+---+---+---+---+---+
1 | r | n | b | q | k | b | n | r | 1
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

Black's Move
>
```

## Goal #3:  Detect when the game is over via checkmate
The game should be able to detect when the game is over, either from a Checkmate or a Draw.  You can ignore the possibility of a game ending by repetitive moves, or by there not being enough pieces left on either side to complete the game.  For instance, if the game were to follow the simple Fool's Mate game (http://en.wikipedia.org/wiki/Fool's_mate), the final move in the CLI will look like:

```Shell
Black's Move
> move d8 h4
    a   b   c   d   e   f   g   h
  +---+---+---+---+---+---+---+---+
8 | R | N | B |   | K | B | N | R | 8
  +---+---+---+---+---+---+---+---+
7 | P | P | P | P |   | P | P | P | 7
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   | 6
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   | P |   |   |   | 5
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   | p | Q | 4
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   | p |   |   | 3
  +---+---+---+---+---+---+---+---+
2 | p | p | p | p | p |   |   | p | 2
  +---+---+---+---+---+---+---+---+
1 | r | n | b | q | k | b | n | r | 1
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

The game is over.  Congrats to Black.
>
```

# Congrats!
If you've met the goals above, congratulations!  Please send us a Pull Request so we can review your work.
