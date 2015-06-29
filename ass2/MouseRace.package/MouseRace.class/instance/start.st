as yet unclassified
start
	"Starts the mouse race :)"

	| boardModel |
	FieldMorph loadImageFiles.

	boardModel := Board importFromFile: 'Map0.txt'.
	movementAlgorithm := MovementAlgorithm new board: boardModel.
	board := BoardMorph new: boardModel withAlgorithm: movementAlgorithm.
	board openInWorld