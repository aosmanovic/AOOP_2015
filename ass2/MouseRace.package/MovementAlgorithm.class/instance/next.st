as yet unclassified
next
	"calculate the next steps for all mice"

	board mice
"	(1 to: board mice)"
		do: [ :mouse | 
			| freeNeighbors closestNeighbor |
			"mouse := board mice at: i."

			"get free neighbor fields"
			freeNeighbors := self getFreeNeighbors: mouse coordinates.

			"check for dead end..!?"

			"check for a crash..!?"

			"calculate state change..!?"

			"calculate a simple closest neighbor"
			closestNeighbor := self getClosestNeighbor: freeNeighbors toCheese: board cheeseCoordinates.
			mouse coordinates: closestNeighbor ]