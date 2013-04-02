package edu.rit.cs.freeforall.model;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class ClockTest {
	private Clock clock;
	private Board mockBoard;

	@Before
	public void setUp() {
		this.clock = new Clock();
		this.mockBoard = mock(Board.class);
		
		this.clock.useBoard(this.mockBoard);
	}
	
	/**
	 * Make sure that the clock can advance/spawn pieces periodically.
	 */
	@Test
	public void test() {
		long iterations = Math.max(Clock.SPAWN_TIME_UNITS, Clock.ADVANCE_TIME_UNITS) + 1;
		for (int i = 0; i < iterations; i++)
			this.clock.onTick();
		
		verify(this.mockBoard, atLeast(1)).advancePieces();
		verify(this.mockBoard, atLeast(1)).spawnPieces();
	}

}
