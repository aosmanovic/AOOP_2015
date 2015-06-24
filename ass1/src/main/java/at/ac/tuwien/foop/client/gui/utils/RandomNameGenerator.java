package at.ac.tuwien.foop.client.gui.utils;

import java.util.Random;

public class RandomNameGenerator {

	final static private String[] names = { "Charles Babbage", "George Boole",
			"Vannevar Bush", "Alan Turing", "Edgar Codd", "John Backus",
			"Dennis Ritchie", "James Gosling", "Bjarne Stroustrup",
			"Guido vanRossum", "Yukihiro Matsumoto", "Gavin King",
			"Ken Thompson", "Linus Torvalds", "Richard Stallman",
			"Tim Berners-Lee", "Ada Lovelace", "Heinz Zemanek",
			"Gottfried-Wilhelm Leibnitz", "Joseph-Marie Jacquard",
			"Alonzo Church", "Konrad Zuse", "Grace Hopper", "John vonNeumann" };
	
	public static String name() {
		return names[new Random().nextInt(names.length - 1)].split(" ")[0] + " " + names[new Random().nextInt(names.length - 1)].split(" ")[1];
	}
}
