as yet unclassified
next
	"calculate the next steps for all mice"

	board mice
		do: [ :mouse | 
			| freeNeighbors closestNeighbor |
			"get free neighbor fields"
			freeNeighbors := self getFreeNeighbors: mouse coordinates.

			"check for dead end..!?"

			"check for a crash..!?"

			"calculate state change..!?"

			"calculate a simple closest neighbor"
			closestNeighbor := self getClosestNeighbor: freeNeighbors toCheese: board cheeseCoordinates.
			mouse coordinates: closestNeighbor.

			"if cheese we are done"
			(self isCheese: closestNeighbor)
				ifTrue: [ board running: false ] ].
	board resetWind