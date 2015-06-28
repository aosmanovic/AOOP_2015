accessing
board: anObject
	board := anObject.

	"iterate lines"
	(1 to: board fields size)
		do: [ :i | 
			| line |
			line := board fields at: i.

			"iterate fields"
			(1 to: line size)
				do: [ :j | 
					| field |
					field := line at: j.

					"create a field morph and add it "
					self
						addMorph:
							(FieldMorph new: field type withCoordinates: ((j - 1) * FieldMorph imageSize) @ ((i - 1) * FieldMorph imageSize)) ] ]