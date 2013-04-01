package edu.rit.cs.freeforall.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
	@Test
	public void testEquals() {
		Location a = new Location(2, 3);
		Location b = new Location(2, 3);
		
		assertEquals(a, b);
	}
	
	@Test
	public void testWrapAroundInitialValues() {
		Location wrapped = new Location(-1, -1);
		Location normalized = new Location(Location.MAX_ROWS - 1,
				Location.MAX_COLUMNS - 1);
		
		assertEquals(normalized, wrapped);
	}
	
	@Test
	public void testAfterDelta() {
		Location start = new Location(0, 0);
		Location end = start.after(-3, -2);
		
		Location expected = new Location(Location.MAX_ROWS - 3, Location.MAX_COLUMNS - 2);
		
		assertEquals(expected, end);
	}
}
