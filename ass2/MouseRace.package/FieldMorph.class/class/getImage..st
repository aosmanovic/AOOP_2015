as yet unclassified
getImage: type
	type = 'floor'
		ifTrue: [ ^ FloorForm ].
	type = 'wall'
		ifTrue: [ ^ WallForm ].
	type = 'cheese'
		ifTrue: [ ^ CheeseForm ].
	type = 'mouse'
		ifTrue: [ ^ MouseForm ].
	Exception new messageText: 'Undefined image type!' signal.
	