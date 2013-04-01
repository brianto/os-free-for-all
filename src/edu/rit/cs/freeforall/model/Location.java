package edu.rit.cs.freeforall.model;

public class Location {
	public static final int MAX_ROWS = 6; // TODO move into preferences
	public static final int MAX_COLUMNS = 6; // TODO move into preferences
	
	private final int row;
	private final int column;
	
	public Location(int row, int column) {
		while (row < 0)
			row += MAX_ROWS;
		
		while (column < 0)
			row += MAX_COLUMNS;
		
		this.row = row % MAX_ROWS;
		this.column = column % MAX_COLUMNS;
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

	public Location after(int deltaRow, int deltaColumn) {
		return new Location(this.row + deltaRow, this.column + deltaColumn);
	}
}
