as yet unclassified
start
	"Starts the mouse race :)"

	FieldMorph loadImageFiles.

	boardModel := Board importFromFile: 'Map0.txt'.
	movementAlgorithm := MovementAlgorithm new board: boardModel.
	board := BoardMorph new: boardModel withAlgorithm: movementAlgorithm.
	board openInWorld.
	boardModel running: true