package model;

public class User {

	private String nickname;
	private int score;
	
	public User(String nickname, int score) {
		this.nickname = nickname;
		this.score = score;
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
}
