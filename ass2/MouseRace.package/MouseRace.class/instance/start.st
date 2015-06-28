as yet unclassified
start
	"Starts the mouse race :)"

	FieldMorph loadImageFiles.

	board := BoardMorph new: (Board importFromFile: 'Map0.txt') withAlgorithm: MovementAlgorithm new.
	board openInWorld
