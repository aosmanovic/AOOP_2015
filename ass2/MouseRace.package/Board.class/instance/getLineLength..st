as yet unclassified
getLineLength: string
	
	"Import a string and create a board out of it."
	|lineLength|
	
	"Check that all lines have the same length."
	lineLength := 0.
	string
		linesDo: [:line | lineLength = 0
			ifTrue: [lineLength := line size]
			ifFalse: [lineLength = line size
				ifFalse: [self inform: 'wrong data for game board!'.
				(Exception new messageText: 'wrong data for game board!') signal]]].
	^ lineLength 