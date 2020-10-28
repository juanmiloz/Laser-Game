package model;

public class User {

	private String nickname;
	private int score;
	private User father;
	private User left;
	private User right;
	
	public User(String nickname, int score) {
		this.nickname = nickname;
		this.score = score;
		father = null;
		left = null;
		right = null;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setFather(User father) {
		this.father = father;
	}
	
	public User getFather() {
		return father;
	}
	
	public void setLeft(User left) {
		this.left = left;
	}
	
	public User getLeft() {
		return left;
	}
	
	public void setRight(User right) {
		this.right = right;
	}
	
	public User getRight() {
		return right;
	}
}
