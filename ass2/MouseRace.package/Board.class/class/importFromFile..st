as yet unclassified
importFromFile: filename
	"Import a file."

	| content |
	content := (StandardFileStream fileNamed: filename) contentsOfEntireFile.
	Board new import: content