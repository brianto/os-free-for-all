package edu.rit.cs.freeforall.model;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.util.Log;

public class Piece extends Thread {
	private final Team team;
	private Board board;

	private volatile boolean running;
	private volatile CountDownLatch latch;

	private int rowTraversalDirection;
	private int columnTraversalDirection;
	private boolean traversingHorizontally;

	public Piece(Team team) {
		this.team = team;

		this.rowTraversalDirection = 1;
		this.columnTraversalDirection = 1;
		this.traversingHorizontally = true;

		this.running = true;
	}

	public Piece(Team team, Random random) {
		this(team);

		this.rowTraversalDirection = random.nextBoolean() ? 1 : -1;
		this.columnTraversalDirection = random.nextBoolean() ? 1 : -1;
		this.traversingHorizontally = random.nextBoolean();
	}

	public Piece(Team team, int rowDirection, int columnDirection,
			boolean horizontalFirst) {
		this(team);

		this.rowTraversalDirection = rowDirection;
		this.columnTraversalDirection = columnDirection;
		this.traversingHorizontally = horizontalFirst;
	}

	@Override
	public void run() {
		try {
			while (this.running)
				this.doIteration();

			Log.i("Free For All", "Piece Terminated");
		} catch (InterruptedException e) {
			Log.wtf("Free For All", e.getMessage());
		}
	}

	private void doIteration() throws InterruptedException {
		synchronized (this) {
			this.wait();
			this.latch.await();
		}

		this.move();
	}

	protected void move() {
		// Replace with strategy pattern for determining movement
		if (this.traversingHorizontally) {
			this.board.movePiece(this, 0, this.columnTraversalDirection);
		} else {
			this.board.movePiece(this, this.rowTraversalDirection, 0);
		}

		this.traversingHorizontally = !this.traversingHorizontally;
	}

	public Piece useBoard(Board board) {
		this.board = board;
		return this;
	}

	public Piece useLatch(CountDownLatch latch) {
		this.latch = latch;
		return this;
	}

	public void finish() {
		this.running = false;
	}

	public boolean isRunning() {
		return this.running;
	}

	public Team getTeam() {
		return this.team;
	}
}
