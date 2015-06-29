as yet unclassified
getFreeNeighbors: coordinates
	| fields c |
	fields := OrderedCollection new.

	c := (coordinates x - 1) @ coordinates y.
	c x < 0 | ((board at: c) type = 'wall')
		ifFalse: [ fields add: c ].
	c := (coordinates x + 1) @ coordinates y.
	c x > (board fields at: 1) size | ((board at: c) type = 'wall')
		ifFalse: [ fields add: c ].
	c := coordinates x @ coordinates y - 1.
	c y < 0 | ((board at: c) type = 'wall')
		ifFalse: [ fields add: c ].
	c := coordinates x @ coordinates y + 1.
	c y > board fields size | ((board at: c) type = 'wall')
		ifFalse: [ fields add: c ].

	^ fields