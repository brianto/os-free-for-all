package edu.rit.cs.freeforall.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class PieceTest {
	private static final Team TEAM = Team.BLUE;

	private Board mockBoard;
	private Piece piece;

	@Before
	public void setUp() throws Exception {
		this.mockBoard = mock(Board.class);
	}

	@Test
	public void testLeftwardsUpwardsTraversal() {
		this.piece = new Piece(TEAM, -1, -1, true).useBoard(this.mockBoard);

		this.piece.move();
		this.piece.move();

		verify(this.mockBoard).movePiece(this.piece, 0, -1); // Move Left
		verify(this.mockBoard).movePiece(this.piece, -1, 0); // Move Up
	}

	@Test
	public void testDownwardsRightwardsTraversal() {
		this.piece = new Piece(TEAM, 1, 1, false).useBoard(this.mockBoard);

		this.piece.move();
		this.piece.move();

		verify(this.mockBoard).movePiece(this.piece, 1, 0); // Move Down
		verify(this.mockBoard).movePiece(this.piece, 0, 1); // Move Right
	}

	@Test
	public void testRightwardsUpwardsTraversal() {
		this.piece = new Piece(TEAM, -1, 1, true).useBoard(this.mockBoard);

		this.piece.move();
		this.piece.move();

		verify(this.mockBoard).movePiece(this.piece, 0, 1); // Move Right
		verify(this.mockBoard).movePiece(this.piece, -1, 0); // Move Up
	}
}
