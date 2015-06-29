This model represents the board of the game including all fields.

A board can be imported from a string or directly from a file containing a board string.
Example ow to import from file:  board := Board importFromFile: '/path/to/Map0.txt'.

Board format:
A 'w' is a wall, '-' a floor tile, 'm' a starting position for a mouse and 'C' the cheese as the goal.

Here is an example:
wwwwww
w---mw
w-wwww
w---Cw
wwwwww

TODO: 
- rename messages
- check for wind max