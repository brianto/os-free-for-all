package edu.rit.cs.freeforall.model;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
	public static final long TIME_UNIT_DURATION = 200;  // TODO put in resources
	public static final long ADVANCE_TIME_UNITS = 5; // TODO put in resources
	public static final long SPAWN_TIME_UNITS = 6 * 5; // TODO put in resources

	private Timer timer;
	private Board board;
	private boolean active;
	
	private volatile long advanceTime;
	private volatile long spawnTime;

	public Clock() {
		this.timer = new Timer();
		
		this.advanceTime = 0;
		this.spawnTime = 0;

		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Clock.this.onTick();
			}
		}, 0, TIME_UNIT_DURATION);
	}

	public Clock withBoard(Board board) {
		this.board = board;
		return this;
	}
	
	private void onTick() {
		if (!this.active)
			return;

		if (this.advanceTime == 0)
			this.advancePieces();
		
		if (this.spawnTime == 0)
			this.spawnPieces();
		
		this.advanceTime = (this.advanceTime + 1) % ADVANCE_TIME_UNITS;
		this.spawnTime = (this.spawnTime + 1) % SPAWN_TIME_UNITS;
	}

	private void advancePieces() {
		this.board.advancePieces();
	}

	private void spawnPieces() {
		this.board.spawnPieces();
	}
	
	public void stop() {
		this.timer.cancel();
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
