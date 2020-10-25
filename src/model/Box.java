package model;

public class Box {

	Box left;
	Box right;
	Box up;
	Box down;
	String mirror;
	int numRow;
	int numColumn;
	
	public Box(int numRow, int numColumn) {
		left = null;
		right = null;
		up = null;
		down = null;
		mirror = null;
		this.numRow = numRow;
		this.numColumn = numColumn;
	}
	
	public void setLeft(Box left) {
		this.left = left;
	}
	
	public Box getLeft() {
		return left;
	}
	
	public void setRight(Box right) {
		this.right = right;
	}
	
	public Box getRight() {
		return right;
	}
	
	public void setUp(Box up) {
		this.up = up;
	}
	
	public Box getUp() {
		return up;
	}
	
	public void setDown(Box down) {
		this.down = down;
	}
	
	public Box getDown() {
		return down;
	}
	
	public void setNumRow(int numRow) {
		this.numRow = numRow;
	}
	
	public int getNumRow() {
		return numRow;
	}
	
	public void setNumColumn(int numColumn) {
		this.numColumn = numColumn;
	}
	
	public int getNumColumn() {
		return numColumn; 
	}
}