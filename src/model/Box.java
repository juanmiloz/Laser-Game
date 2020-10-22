package model;

public class Box {

	Box left;
	Box right;
	Box up;
	Box down;
	
	public Box() {
		left = null;
		right = null;
		up = null;
		down = null;
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
}
