package edu.rit.cs.freeforall.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Board extends Observable {
	private ConcurrentHashMap<Piece, Location> spaces;
	private Map<Team, AtomicInteger> scores;

	public Board() {
		this(new ConcurrentHashMap<Piece, Location>(),
				new HashMap<Team, AtomicInteger>(Team.values().length));

		for (Team team : Team.values())
			this.scores.put(team, new AtomicInteger(0));
	}

	public Board(ConcurrentHashMap<Piece, Location> spaces,
			HashMap<Team, AtomicInteger> scores) {
		this.spaces = spaces;
		this.scores = scores;
	}

	public void advancePieces() {
		CountDownLatch latch = new CountDownLatch(this.spaces.size());

		for (Piece piece : this.spaces.keySet()) {
			piece.useLatch(latch);

			synchronized (piece) {
				piece.notify();
			}
		}

		this.notifyObservers();
	}

	public void spawnPieces() {
		for (Team team : Team.values()) {
			Piece piece = new Piece(team);
			Location location = Location.random();

			this.spawnPiece(piece, location);

			piece.start();
		}

		this.notifyObservers();
	}

	public void spawnPiece(Piece piece, Location location) {
		this.maybeEvictPiece(location);
		this.spaces.put(piece, location);

		this.setChanged();
	}

	/**
	 * Tries to move a piece.
	 * 
	 * This method evicts other pieces if necessary and adjusts the score to
	 * reflect eviction.
	 * 
	 * If a piece has already been evicted by a another piece, the piece will
	 * not move.
	 * 
	 * @param piece
	 *            target piece to move
	 * @param deltaRow
	 *            row(s) up/down to move (negative is up)
	 * @param deltaColumn
	 *            row(s) left/right to move (negative is left)
	 *            
	 * @return <code>true</code> if a piece was evicted.
	 */
	public synchronized boolean movePiece(Piece piece, int deltaRow,
			int deltaColumn) {
		assert this.spaces.containsKey(piece);

		if (!piece.isRunning())
			return false;

		Location location = this.spaces.get(piece);
		Location destination = location.after(deltaRow, deltaColumn);

		boolean evictedPiece = this.maybeEvictPiece(destination);

		if (evictedPiece)
			this.scores.get(piece.getTeam()).incrementAndGet();

		this.spaces.replace(piece, destination);
		this.setChanged();

		return evictedPiece;
	}

	private boolean maybeEvictPiece(Location target) {
		Piece suspect = null;

		if (!this.spaces.values().contains(target))
			return false;

		for (Map.Entry<Piece, Location> entry : this.spaces.entrySet()) {
			Piece candidate = entry.getKey();
			Location location = entry.getValue();

			if (target.equals(location)) {
				suspect = candidate;
				break;
			}
		}

		suspect.finish();
		this.spaces.remove(suspect);

		return true;
	}
}
