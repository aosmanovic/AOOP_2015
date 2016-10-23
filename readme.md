# Network Game

**Institution:** TU Vienna

**Academic year:** 2014/2015

**Subject:** Advanced Object-Oriented Programming

**Course description**

"Participants become acquainted with advanced techniques in object-oriented programming and learn to distinguish between promising and dangerous uses of these techniques. Furthermore, partitipants learn how to bypass shortcomings of current programming languages by applying appropriate programming techniques. We deal among others with the following topics on the basis of several object-oriented programming languages (Java, C# or C++ as well as Eiffel and Smalltalk):

* Names as means of abstraction
* circumvention of name conflicts
* substitutability versus genericity
* how to handle covariant problems and parallel class hierarchies
* implementation of object-oriented languages
* selected software design patterns" TISS TU Vienna

Team work - group of 3 people

**Application type:** Desktop

**Programming language:** Java

**IDE:** Eclipse

## Task Description

"Design and implement a network-compatible mouse racing game in an object-oriented programming language of your choice (but not in a language specialized for game development). The game shall correspond to the following description.
Each player is represented by a mouse searching for a piece of cheese located at the playing field. Mice pace around rather quickly within the boarders of the playing field according to a predefined algorithm (to be determined by the game designer) depending on environmental influences. Such influences origin from different kinds of barriers and artefacts (walls, fences, lakes, toys, food, toxins, fragrances, traps, etc.) causing a mouse to change its behavior, direction or speed for a while or forever. Players can influence the race by adding artefacts to and removing them from the playing field during the race. Winner is the first mouse finding a piece of cheese.

Each player uses his/her own computer connected to a network. The communication between the computers shall be efficient enough to avoid noticeable delays for up to four players. All players shall get the same information at about the same time.

Please select appropriate details of the game by yourselves. Give your fancy full scope." LVA team

### Important commands


* run the server shell: _mvn exec:java -Dexec.mainClass="at.ac.tuwien.foop.server.shell.ServerShell"_
* run the client shell: _mvn exec:java -Dexec.mainClass="at.ac.tuwien.foop.client.shell.ClientShell"_
