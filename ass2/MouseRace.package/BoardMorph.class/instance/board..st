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
					self addMorph: (FieldMorph new: field type withCoordinates: (j @ i - 1) * FieldMorph imageSize) ] ].

	"create mice"
	mice := Array new: board mice size.
	(1 to: board mice size)
		do: [ :i | 
			| mouse |
			mouse := FieldMorph new: 'mouse' withCoordinates: ((board mice at: i) coordinates - 1) * FieldMorph imageSize.
			self addMorph: mouse.
			mice at: i put: mouse ]