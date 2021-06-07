package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;
import java.util.Random;

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
				slot++;
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
		int dim = this.board.length;
		if(row >= 0 && row < dim && column >= 0 && column < dim)
			return this.board[row][column];
		return -1;
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
		
		return this;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		if(pathLength == 0)
			return this;
		SimplePuzzleState next = new SimplePuzzleState();
		SimplePuzzleState old = this;
		int i = 0;
		while(i < pathLength) {
			int[][] empties = new int[2][3]; 
			int numberOfEmptySlots = 0;
			int dim = old.board.length;
			for (int j = 0; j < dim; j++) {
				for (int k = 0; k < dim; k++) {
					if(old.isEmpty(j, k)) {
						empties[0][numberOfEmptySlots] = j;
						empties[1][numberOfEmptySlots] = k;
						numberOfEmptySlots++;
					}
				}
			}	
			Random generateRandom = new Random();
			int randomEmptySlot = generateRandom.nextInt(numberOfEmptySlots);
			int row = empties[0][randomEmptySlot];
			int column = empties[1][randomEmptySlot];
			int randomMoves = generateRandom.nextInt(4);
			Operation op = null;
			switch(randomMoves) {
				case 0:
					op = Operation.MOVEUP;
					if(old.getValue(row+1, column) > 0) {
						next = (SimplePuzzleState) old.move(row+1, column, op);
						i++;
						old = next;
					}
					break;
				case 1:
					op = Operation.MOVEDOWN;
					if(old.getValue(row-1, column) > 0) {
						next = (SimplePuzzleState) old.move(row-1, column, op);
						i++;
						old = next;
					}
					break;
				case 2:
					op = Operation.MOVELEFT;
					if(old.getValue(row, column+1) > 0) {
						next = (SimplePuzzleState) old.move(row, column+1, op);
						i++;
						old = next;
					}
					break;
				case 3: 
					op = Operation.MOVERIGHT;
					if(old.getValue(row, column-1) > 0) {
						next = (SimplePuzzleState) old.move(row, column-1, op);
						i++;
						old = next;
					}
					break;
			}
		}
		return next;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		if(this.board[row][column] == 0)
			return true;
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		return this;
	}
	
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || o.getClass() != this.getClass())
			return false;
		SimplePuzzleState state = (SimplePuzzleState) o;
		return Arrays.deepEquals(this.board, state.board);
	}
	
	public int hashCode() {
		int hash = 3;
		hash = 31 * hash + ((this.board == null) ? 0 : Arrays.deepHashCode(this.board));
		return hash;
	}

}
