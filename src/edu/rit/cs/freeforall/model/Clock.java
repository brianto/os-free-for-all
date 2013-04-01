package edu.rit.cs.freeforall.model;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
	public static final long ADVANCE_INTERVAL = 1000;
	public static final long SPAWN_INTERVAL = 6 * 1000;

	private Timer timer;
	private Board board;
	private boolean active;

	public Clock() {
		this.timer = new Timer();

		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (Clock.this.active)
					Clock.this.advance();
			}
		}, 0, ADVANCE_INTERVAL);

		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (Clock.this.active)
					Clock.this.spawnPieces();
			}
		}, 0, SPAWN_INTERVAL);
	}
	
	public Clock withBoard(Board board) {
		this.board = board;
		return this;
	}

	private void advance() {
		// TODO Auto-generated method stub
	}

	private void spawnPieces() {
		// TODO Auto-generated method stub
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
