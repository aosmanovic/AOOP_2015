as yet unclassified
start
	"Starts the mouse race :)"

	board := BoardMorph new: (Board importFromFile: 'Map0.txt') withAlgorithm: MovementAlgorithm new.
	board showInWorld
