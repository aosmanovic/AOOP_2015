as yet unclassified
import: string
	"Import a string and create a board out of it."

	| lineLength lineNumber mouseNumber |
	"Check that all lines have the same length."
	lineLength := self getLineLength: string.	"Create the fields of the board."

	fields := Array new: string lineCount.
	mice := Array new: 4.
	Transcript show: lineLength.

	lineNumber := 1.
	mouseNumber := 1.

	string
		linesDo: [ :line | 
			| char lineFields field |
			"Create an array for each line."
			lineFields := Array new: lineLength.
			fields at: lineNumber put: lineFields.

			"Create a field for each character."
			(1 to: line size)
				do: [ :i | 
					char := line at: i.
					field := self getField: char withCoordinates: i @ lineNumber.
					lineFields at: i put: field ].
			lineNumber := lineNumber + 1 ]