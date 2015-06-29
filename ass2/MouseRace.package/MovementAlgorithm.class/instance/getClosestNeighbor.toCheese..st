as yet unclassified
getClosestNeighbor: coordinates toCheese: cheese
	"Return one pair of coordinates."

	| closest |
	coordinates
		inject: 9999
		into: [ :min :item | 
			| dst |
			dst := item dist: cheese.
			dst < min
				ifTrue: [ 
						closest := item.
						dst ]
				ifFalse: [ min ] ].
	^ closest