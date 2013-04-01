package edu.rit.cs.freeforall.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;
	
	private ConcurrentHashMap<Piece, Location> spaces;
	private HashMap<Team, AtomicInteger> scores;

	@Before
	public void setUp() throws Exception {
		this.spaces = new ConcurrentHashMap<Piece, Location>();
		this.scores = new HashMap<Team, AtomicInteger>();
		
		for (Team team : Team.values())
			this.scores.put(team, new AtomicInteger(0));
		
		this.board = new Board(this.spaces, this.scores);
	}

	@Test
	public void testSpawnWithoutEviction() {
		Piece piece = new Piece(Team.BLUE, 1, 1, true);
		Location location = new Location(0, 0);
		
		this.board.spawnPiece(piece, location);
		
		assertTrue(this.spaces.containsKey(piece));
		assertTrue(this.spaces.containsValue(location));
	}

	@Test
	public void testSpawnWithEviction() {
		Piece begin = new Piece(Team.BLUE, 1, 1, true);
		Piece end = new Piece(Team.RED, 1, 1, true);
		Location start = new Location(0, 0);
		Location finish = new Location(0, 0);
		
		this.board.spawnPiece(begin, start);
		this.board.spawnPiece(end, finish);
		
		assertFalse(this.spaces.containsKey(begin));
		assertTrue(this.spaces.containsKey(end));
	}
}
