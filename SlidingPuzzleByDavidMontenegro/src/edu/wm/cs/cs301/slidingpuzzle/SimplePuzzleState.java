package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {
	
	private PuzzleState parent;
	private Operation operation;
	private int pathLength;
	
	public SimplePuzzleState() {
		this.parent = null;
		this.operation = null;
	}
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		this.parent = null;
		this.operation = null;
		this.pathLength = 0;
	}

	@Override
	public int getValue(int row, int column) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setParent(PuzzleState parent) {
		this.parent = parent;
	}
	
	@Override
	public PuzzleState getParent() {
		return this.parent;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
