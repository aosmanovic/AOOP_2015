input events
keyDown: event
	"create wind or start/stop game"

	event keyCharacter = $a | (event keyCharacter = $j)
		ifTrue: [ ^ board sendGust: -1 @ 0 ].
	event keyCharacter = $d | (event keyCharacter = $l)
		ifTrue: [ ^ board sendGust: 1 @ 0 ].
	event keyCharacter = $w | (event keyCharacter = $i)
		ifTrue: [ ^ board sendGust: 0 @ -1 ].
	event keyCharacter = $s | (event keyCharacter = $k)
		ifTrue: [ ^ board sendGust: 0 @ 1 ]