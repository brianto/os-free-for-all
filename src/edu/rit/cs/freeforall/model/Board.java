package edu.rit.cs.freeforall.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Board extends Observable {
	private Map<Piece, Location> spaces;

	public Board() {
		this.spaces = new HashMap<Piece, Location>();
	}
}
