as yet unclassified
getField: char withCoordinates: coordinates
	"Creates a Field based on the input character."

	char = $w
		ifTrue: [ ^ Field newWall: coordinates ].
	char = $-
		ifTrue: [ ^ Field newFloor: coordinates ].
	char = $m
		ifTrue: [ ^ Field newFloor: coordinates ].
	char = $C
		ifTrue: [ ^ Field newCheese: coordinates ]