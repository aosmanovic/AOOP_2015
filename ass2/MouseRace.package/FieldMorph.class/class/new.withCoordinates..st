as yet unclassified
new: type withCoordinates: coordinates
"Create a new field morph.
type = string, coordinates = (x@y)"
|field|
field :=self new.

field image: (FieldMorph getImage: type).
field position: coordinates.