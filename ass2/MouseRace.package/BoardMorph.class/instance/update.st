setup
update
	board running
		ifFalse: [ ^ self ].

	(1 to: mice size) do: [ :i | (mice at: i) position: ((board mice at: i) coordinates - 1) * FieldMorph imageSize ]