package edu.wm.cs.cs301.slidingpuzzle;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

public class SimplePuzzleState implements PuzzleState {
	
	private int[][] board;
	private PuzzleState parent;
	private Operation operation;
	private int pathLength;
	
	public SimplePuzzleState() {
		this.board = null;
		this.parent = null;
		this.operation = null;
		this.pathLength = 0;
	}
	
	public SimplePuzzleState(int boardLength) {
		this.board = new int[boardLength][boardLength];
		this.parent = null;
		this.operation = null;
		this.pathLength = 0;
	}
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		this.board = new int[dimension][dimension];
		int slot = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				slot = slot + 1;
				this.board[i][j] = slot;
				if(i == dimension - 1 && j >= dimension - numberOfEmptySlots)
					this.board[i][j] = 0; 
			}
		}
		this.parent = null;
		this.operation = null;
		this.pathLength = 0;
	}

	@Override
	public int getValue(int row, int column) {
		return this.board[row][column];
	}
	
	@Override
	public PuzzleState getParent() {
		return this.parent;
	}
	
	@Override
	public Operation getOperation() {
		return this.operation;
	}

	@Override
	public int getPathLength() {
		return this.pathLength;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		if(this.isEmpty(row, column))
			return null;
		int dim = this.board.length;
		SimplePuzzleState state = new SimplePuzzleState(dim);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				state.board[i][j] = this.getValue(i,j);
			}
		}	
		switch(op) {
			case MOVEUP:
				if(row - 1 >= 0 && this.isEmpty(row - 1, column)) {
					int value = getValue(row, column);
					state.board[row][column] = 0;
					state.board[row-1][column] = value;
					state.parent = this;
					state.operation = op;
					state.pathLength = this.pathLength + 1;
				}	
				else
					return null;
				break;
			case MOVEDOWN:
				if(row + 1 < dim && this.isEmpty(row + 1, column)) {
					int value = getValue(row, column);
					state.board[row][column] = 0;
					state.board[row+1][column] = value;
					state.parent = this;
					state.operation = op;
					state.pathLength = this.pathLength + 1;
				}
				else
					return null;
				break;
			case MOVELEFT:
				if(column - 1 >= 0 && this.isEmpty(row, column - 1)) {
					int value = getValue(row, column);
					state.board[row][column] = 0;
					state.board[row][column-1] = value;
					state.parent = this;
					state.operation = op;
					state.pathLength = this.pathLength + 1;
				}
				else
					return null;
				break;
			case MOVERIGHT:
				if(column + 1 < dim && this.isEmpty(row, column + 1)) {
					int value = getValue(row, column);
					state.board[row][column] = 0;
					state.board[row][column+1] = value;
					state.parent = this;
					state.operation = op;
					state.pathLength = this.pathLength + 1;
				}	
				else
					return null;
				break;
		}
		return state;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		if(this.isEmpty(startRow, startColumn))
			return null;
		if(this.isEmpty(endRow, endColumn) != true)
			return null;
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		if(this.board[row][column] == 0)
			return true;
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || o.getClass() != this.getClass())
			return false;
		SimplePuzzleState state = (SimplePuzzleState) o;
		return this.pathLength == state.pathLength && this.operation == state.operation 
				&& this.parent == state.parent && Arrays.deepEquals(this.board, state.board);
	}
	
	public int hashCode() {
		int hash = 3;
		hash = 31 * hash + this.pathLength;
		hash = 31 * hash + ((this.parent == null) ? 0 : this.parent.hashCode());
		hash = 31 * hash + ((this.operation == null) ? 0 : this.operation.hashCode());
		return hash;
	}

}
