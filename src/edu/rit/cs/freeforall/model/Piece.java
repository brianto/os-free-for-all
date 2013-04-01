package edu.rit.cs.freeforall.model;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.util.Log;

public class Piece extends Thread {
	private static final Random random = new Random();
	
	private final Team team;
	private Board board;
	
	private volatile boolean running;
	private volatile CountDownLatch latch;
	
	private final int rowTraversalDirection;
	private final int columnTraversalDirection;
	private boolean traversingHorizontally;
	
	public Piece(Team team) {
		this.team = team;
		
		this.running = true;
		
		this.rowTraversalDirection = Piece.random.nextBoolean() ? 1 : -1;
		this.columnTraversalDirection = Piece.random.nextBoolean() ? 1 : -1;
		this.traversingHorizontally = Piece.random.nextBoolean();
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
		
		// Replace with strategy pattern for determining movement
		if (this.traversingHorizontally) {
			this.board.movePiece(this, this.rowTraversalDirection, 0);
		} else {
			this.board.movePiece(this, 0, this.columnTraversalDirection);
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

	public Team getTeam() {
		return this.team;
	}
}
