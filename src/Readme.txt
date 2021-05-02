PROJECT TITLE: Conway's Game of Life
PURPOSE OF PROJECT: To emulate Conway's Game of Life
VERSION: Project I
AUTHORS: Arya Frost (30873247)
IDE: Eclipse

REQUIREMENTS:

-The program includes a default 20x20 grid that resizes
alongside the main window.

-A button to reset the simulation is present
-A button to pause/resume the simulation is present

-The program displays the number of steps since the simulation
was most recently reset

-Implemented flourishes:

-A slider has been included to change the speed
of the simulation
-A slider has been included to change the density
of cells that are alive
-Dead cells are colored red, and alive cells are colored in green

NOTE ABOUT CLASSES

I used one custom class that extends JPanel called GameBoard.
This class draws the grid but reads all values from my main class.

NOTES ABOUT PROJECT
As far as I can tell the project works as intended, and implements
all required methods that satisfy the directives of our assignment

I implemented a 2D matrix that stores the state of each cell. A
second matrix stores the state of the cell's neighbors, this is
then used to update the state of each cells each iteration.

The gameBoard uses this matrix to paint a grid where the color
of each cell is determined by it's state.