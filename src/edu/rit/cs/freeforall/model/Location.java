package edu.rit.cs.freeforall.model;

public class Location {
	private final int row;
	private final int column;
	
	public Location(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public int hashCode() {
		return this.row * 37 + this.column;
	}
	
	@Override
	public boolean equals(Object o) {
		Location other;
		
		if (o instanceof Location) {
			other = (Location) o;
		} else {
			return false;
		}
		
		return this.row == other.row && this.column == other.column;
	}
}
