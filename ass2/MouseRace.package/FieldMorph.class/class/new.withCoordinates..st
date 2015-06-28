as yet unclassified
new: type withCoordinates: coordinates
	"Create a new field morph.
type = string, coordinates = (x@y)"

	^ self new
		image: (FieldMorph getImage: type);
		position: coordinates