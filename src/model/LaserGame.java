package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LaserGame {

	private final static String FILE_SERIALIZABLE_ROOT = "data/serializableRoot.ap2";
	User root;
	Box first;
	int kGlobal; 
	int score;
	
	public LaserGame() {
		
	}
	
	public String createPlayArea(String nickname, int n, int m, int k) {
		first = new Box(1, 1);
		Box current = first;
		createColumns(current, m-1);
		createAllRows(current,n-1);
		createConectionManagement(current);
		generateRandomMirrors(k, n, m);
		kGlobal = k;
		String area = nickname+ "  |  " + k + " espejos restantes por encontrar\n";
		area += printAreaGame(current);
		score = 0;
		
		return area; 
	}
	
	public String startGame(String input, String nickname, int nMatriz,int mMatriz) {
		String answer = "";
		Box upperLeftCorner = searchPosition(first, 1, 1);
		Box lowerLeftCorner = searchPosition(first, nMatriz,1 );
		Box upperRightCorner = searchPosition(first, 1, mMatriz);
		Box lowerRightCorner = searchPosition(first, nMatriz, mMatriz);
		int lenghtInput = input.length();
		
		boolean comprobate = cornerOrNoCorner(input, lenghtInput);
		
		if(input.charAt(0)=='L') {
			int n = (int)(input.charAt(1));
			int m = (int)(input.charAt(2));
			n = n-49+1;
			m = m-65+1;
			String direction = String.valueOf(input.charAt(3));
			String temp = locateMirror(n, m, direction);
			answer += nickname + "  |  " + kGlobal + " espejos restantes por encontrar\n";
			if(temp.equalsIgnoreCase("Se encontro un espejo")) {
				answer += printAreaGame(first)+"\n";
			}else {
				answer += printAreaGame(first,n,m);
			}
			answer += temp;
		}else if(comprobate == false){
			int i = 0; 
			String newNumber = "";
			String number = noCornerPositionN(input,i,lenghtInput-1,newNumber);
			StringBuilder sb = new StringBuilder(number);
			sb.setLength(lenghtInput-1);
			number = String.valueOf(sb);
			int n = Integer.parseInt(number);
			int m = NoCornerPositionM(input, lenghtInput-1);
			Box search = searchPosition(first, n, m);
			Box end = managementMovementsNoCorners(search, nMatriz, mMatriz);
			answer += nickname + "  |  " + kGlobal + " espejos restantes por encontrar\n";
			int nStard = search.getNumRow();
			int mStard = search.getNumColumn();
			System.out.println("el laser entro en: "+ search.getNumRow()+"."+search.getNumColumn());
			int nEnd = end.getNumRow();
			int mEnd = end.getNumColumn();
			answer += printAreaGameWhitLaser(first,nStard,mStard,nEnd,mEnd);
			answer += "El laser salio por " + end.getNumRow() + "." + end.getNumColumn();
		}else if(comprobate == true) {
			int i = 0; 
			String newNumber = "";
			String number = noCornerPositionN(input,i,lenghtInput-2,newNumber);
			StringBuilder sb = new StringBuilder(number);
			sb.setLength(lenghtInput-2);
			number = String.valueOf(sb);
			int n = Integer.parseInt(number);
			int m = NoCornerPositionM(input, lenghtInput-2);
			String direction = String.valueOf(input.charAt(lenghtInput-1));
			Box search = searchPosition(first, n, m);
			Box end = managementMovementCorner(upperLeftCorner, lowerLeftCorner, upperRightCorner, lowerRightCorner, search, direction);
			answer += nickname + "  |  " + kGlobal + " espejos restantes por encontrar\n";
			int nStard = search.getNumRow();
			int mStard = search.getNumColumn();
			System.out.println("el laser entro en: "+ search.getNumRow()+"."+search.getNumColumn());
			int nEnd = end.getNumRow();
			int mEnd = end.getNumColumn();
			answer += printAreaGameWhitLaser(first,nStard,mStard,nEnd,mEnd);
			answer += "El laser salio por " + end.getNumRow() + "." + end.getNumColumn();
		}
		return answer;
	}
	
	public boolean cornerOrNoCorner(String input, int lenght) {
		boolean corner = true;

		if(Character.isDigit(input.charAt(lenght-2))) {
			corner = false; 
		}
		
		return corner;
	}
	
	public String noCornerPositionN(String input,int i, int lenght, String number) {
		if(i<lenght) {
			number = String.valueOf(input.charAt(i));
			number += noCornerPositionN(input,i+1,lenght, number);
		}
		return number;
	}

	public int NoCornerPositionM(String input, int lenght) {
		int m = (int)(input.charAt(lenght));
		m = m-65+1;
		return m;
	}
	//inicio metodos movimiento	
	//Quedo re largo por la cantidad de casos que pueden ocurrir :)
	public Box managementMovementCorner(Box upperLeftCorner, Box lowerLeftCorner, Box upperRightCorner, Box lowerRightCorner, Box current, String direction) {
		if(current.equals(upperLeftCorner)) {
			if(direction.equalsIgnoreCase("R")) {
				current = movementRight(current);
			}else if(direction.equalsIgnoreCase("D")) {
				current = movementDown(current);
			}
		}else if(current.equals(lowerLeftCorner)) {
			if(direction.equalsIgnoreCase("R")) {
				current = movementRight(current);
			}else if(direction.equalsIgnoreCase("U")) {
				current = movementUp(current);
			}
		}else if(current.equals(upperRightCorner)) {
			if(direction.equalsIgnoreCase("L")) {
				current = movementLeft(current);
			}else if(direction.equalsIgnoreCase("D")) {
				current = movementDown(current);
			}
		}else if(current.equals(lowerRightCorner)) {
			if(direction.equalsIgnoreCase("L")) {
				current = movementLeft(current);
			}else if(direction.equalsIgnoreCase("U")) {
				current = movementUp(current);
			}
		}
		return current;
	}
	
	public Box managementMovementsNoCorners(Box current, int nMatriz, int mMatriz) {
		if(current.getNumColumn()==1) {
			current = movementRight(current);
		}else if(current.getNumRow()==1) {
			current = movementDown(current);
		}else if(current.getNumColumn()==mMatriz) {
			current = movementLeft(current);
		}else if(current.getNumRow()==nMatriz) {
			current = movementUp(current);
		}
		return current;
	}
	
	public Box movementRight(Box current) {
		if(current.getMirror()!=null) {
			if(current.getMirror().equalsIgnoreCase("/")) {
				current = verticalUp(current);
			}else if(current.getMirror().equalsIgnoreCase("\\")) {
				current = verticalDown(current);
			}
		}else {
			current = horizontalRight(current);
		}
		return current;
	}
	
	public Box movementLeft(Box current) {
		if(current.getMirror()!=null) {
			if(current.getMirror().equalsIgnoreCase("/")) {
				current = verticalDown(current);
			}else if(current.getMirror().equalsIgnoreCase("\\")) {
				current = verticalUp(current);
			}
		}else {
			current = horizontalLeft(current);
		}
		return current;
	}
	
	public Box movementUp(Box current) {
		if(current.getMirror()!=null) {
			if(current.getMirror().equalsIgnoreCase("/")) {
				current = horizontalRight(current);
			}else if(current.getMirror().equalsIgnoreCase("\\")) {
				current = horizontalLeft(current);
			}
		}else {
			current = verticalUp(current);
		}
		return current;
	}
	
	public Box movementDown(Box current) {
		if(current.getMirror()!=null) {
			if(current.getMirror().equalsIgnoreCase("/")) {
				current = horizontalLeft(current);
			}else if(current.getMirror().equalsIgnoreCase("\\")) {
				current = horizontalRight(current);
			}
		}else {
			current = verticalDown(current);
		}
		return current;
	}
	
	public Box horizontalRight(Box current) {
		if(current.getRight()==null) {
			return current;
		}else if(current.getRight().getMirror()==null) {
			current = horizontalRight(current.getRight());
		}else if(current.getRight().getMirror().equalsIgnoreCase("/")) {
			current = verticalUp(current.getRight()); 
		}else if(current.getRight().getMirror().equalsIgnoreCase("\\")) {
			current = verticalDown(current.getRight());
		}
		return current;
	}
	
	public Box horizontalLeft(Box current) {
		if(current.getLeft()==null) {
			return current;
		}else if(current.getLeft().getMirror()==null) {
			current = horizontalLeft(current.getLeft());
		}else if(current.getLeft().getMirror().equalsIgnoreCase("/")) {
			current = verticalDown(current.getLeft());
		}else if(current.getLeft().getMirror().equalsIgnoreCase("\\")) {
			current = verticalUp(current.getLeft());
		}
		return current;
	}
	
	public Box verticalUp(Box current) {
		if(current.getUp()==null) {
			return current;
		}else if(current.getUp().getMirror()==null) {
			current = verticalUp(current.getUp());
		}else if(current.getUp().getMirror().equalsIgnoreCase("/")) {
			current = horizontalRight(current.getUp());
		}else if(current.getUp().getMirror().equalsIgnoreCase("\\")) {
			current = horizontalLeft(current.getUp());
		}
		return current;
	}
	
	public Box verticalDown(Box current) {
		if(current.getDown()==null) {
			return current;
		}else if(current.getDown().getMirror()==null) {
			current = verticalDown(current.getDown());
		}else if(current.getDown().getMirror().equalsIgnoreCase("/")) {
			current = horizontalLeft(current.getDown());
		}else if(current.getDown().getMirror().equalsIgnoreCase("\\")) {
			current = horizontalRight(current.getDown());
		}
		return current;
	}
	
	//fin metodos movimiento
	
	public void createColumns(Box current ,int m) {
		if(m!= 0) {
			Box newBox = new Box(1, current.getNumColumn()+1);
			current.setRight(newBox);
			newBox.setLeft(current);
			createColumns(newBox, m-1);
		}
	}
	
	public void createAllRows(Box current, int n) {
		if(current != null) {
			createRow(current,n);
			createAllRows(current.getRight(),n);
		}
	} 
	
	public void createRow(Box current,int n) {
		if(n!=0 ) {
			Box newBox = new Box(current.getNumRow()+1,current.getNumColumn());
			current.setDown(newBox);
			newBox.setUp(current);
			createRow(newBox, n-1);
		}
	}
	
	public void createConectionManagement(Box current) {
		if(current != null) {
			addConectionHorizontal(current);
			createConectionManagement(current.getRight());
		}
	}
	
	public void addConectionHorizontal(Box current) {
		if(current.getDown()!=null && current.getRight()!=null) {
			Box newConection = current.getRight().getDown();
			current.getDown().setRight(newConection);
			newConection.setLeft(current.getDown());
			addConectionHorizontal(current.getDown());
		}
	}
	
	public void generateRandomMirrors(int k, int n, int m) {
		if(k!=0) {
			int numRandomN = (int)(Math.random()*n+1);
			int numRandomM = (int)(Math.random()*m+1);
			Box search = searchPosition(first, numRandomN, numRandomM);
			if(search.getMirror() == null) {
				int mirrorRandom = (int)(Math.random()*2+1);
				if(mirrorRandom==1) {
					search.setMirror("/");
				}else if(mirrorRandom==2) {
					search.setMirror("\\");
				}
				generateRandomMirrors(k-1,n,m);
			}else {
				generateRandomMirrors(k, n, m);
			}
		}
	}
	
	public Box searchPosition(Box current, int numRow, int numColumn) {
		Box search = null;
		if(current!=null) {
			if(current.getNumRow() == numRow && current.getNumColumn() == numColumn) {
				return current;
			}else {
				search = searchInRow(current.getRight(),numRow,numColumn);
			}
			if(search==null) {
				search = searchPosition(current.getDown(),numRow,numColumn);
			}
		}
		return search; 
	}
	
	public Box searchInRow(Box current, int numRow, int numColumn) {
		Box search = null;
		if(current!=null) {
			if(current.getNumRow() == numRow && current.getNumColumn() == numColumn) {
				return current;
			}else {
				search = searchInRow(current.getRight(), numRow, numColumn);
			}
		}
		return search;
	}
	
	public String locateMirror(int n, int m, String direction) {
		String answer = "";
		Box searchBox = searchPosition(first, n, m);
		if(direction.equalsIgnoreCase("L")) {
			direction = "/";
		}else if(direction.equalsIgnoreCase("R")) {
			direction = "\\";
		}
		
		if(searchBox.getMirror() == null ) {
			answer += "No se encontro ningun espejo";
			score -= 1;
		}else if(searchBox.getMirror() != null && searchBox.getMirror().equalsIgnoreCase(direction)) {
			searchBox.setVisibility(true);
			answer += "Se encontro un espejo";
			kGlobal -= 1;
			score += 1;
		}
		return answer; 
	}
	
	public String printAreaGame(Box current) {
		String answer = "";
		if(current != null) {
			if(current.getMirror()==null) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==false) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==true){
				answer += "[" + current.getMirror() + "] ";
			}
			answer += printRows(current.getRight());
			answer += printAreaGame(current.getDown());
		}
		return answer;
	}
	
	public String printRows(Box current) {
		String answer = "";
		if(current!= null) {
			if(current.getMirror()==null) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==false) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==true){
				answer += "["+ current.getMirror() + "] ";
			}
			answer += printRows(current.getRight());
		}else {
			answer += "\n";
		}
		return answer;
	}
	
	public String printAreaGame(Box current,int nError, int mError) {
		String answer = "";
		if(current != null) {
			if(current.getVisibility()!=true && current.getNumRow() == nError && current.getNumColumn() == mError) {
				answer += "[X] ";
			}else if(current.getMirror()==null) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==false) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==true){
				answer += "[" + current.getMirror() + "] ";
			}
			answer += printRows(current.getRight(), nError, mError);
			answer += printAreaGame(current.getDown(),nError,mError);
		}
		return answer;
	}
	
	public String printRows(Box current,int nError, int mError) {
		String answer = "";
		if(current!= null) {
			if(current.getVisibility()!=true && current.getNumRow() == nError && current.getNumColumn() == mError) {
				answer += "[X] ";
			}else if(current.getMirror()==null) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==false) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==true){
				answer += "["+ current.getMirror() + "] ";
			}
			answer += printRows(current.getRight(),nError,mError);
		}else {
			answer += "\n";
		}
		return answer;
	}
	
	public String printAreaGameWhitLaser(Box current, int nStart, int mStart, int nEnd, int mEnd) {
		String answer = "";
		if(current != null) {
			if(current.getNumRow() == nStart && current.getNumColumn() == mStart) {
				answer += "[S] ";
			}else if(current.getNumRow()==nEnd && current.getNumColumn()==mEnd) {
				answer += "[E] ";
			}else if(current.getMirror()==null) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==false) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==true){
				answer += "[" + current.getMirror() + "] ";
			}
			answer += printRowsWhitLaser(current.getRight(),nStart, mStart, nEnd, mEnd);
			answer += printAreaGameWhitLaser(current.getDown(),nStart, mStart, nEnd, mEnd);
		}
		return answer;
	}
	
	public String printRowsWhitLaser(Box current, int nStart, int mStart, int nEnd, int mEnd) {
		String answer = "";
		if(current!= null) {
			if(current.getNumRow() == nStart && current.getNumColumn() == mStart) {
				answer += "[S] ";
			}else if(current.getNumRow()==nEnd && current.getNumColumn()==mEnd) {
				answer += "[E] ";
			}else if(current.getMirror()==null) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==false) {
				answer += "[ ] ";
			}else if(current.getMirror()!=null && current.getVisibility()==true){
				answer += "["+ current.getMirror() + "] ";
			}
			answer += printRowsWhitLaser(current.getRight(),nStart,mStart,nEnd,mEnd);
		}else {
			answer += "\n";
		}
		return answer;
	}
	
	public void addUser(User newUser) {
		if(root== null) {
			root = newUser;
		}else {
			addUser(root,newUser);
		}
	}
	
	public void addUser(User current,User newUser) {
		if(newUser.getScore()<=current.getScore()&&current.getLeft()==null) {
			current.setLeft(newUser);
			newUser.setFather(current);
		}else if(newUser.getScore()>current.getScore()&&current.getRight()==null) {
			current.setRight(newUser);
			newUser.setFather(current);
		}else {
			if(newUser.getScore()<=current.getScore() && current.getLeft()!= null) {
				addUser(current.getLeft(),newUser);
			}else {
				addUser(current.getRight(),newUser);
			}
		}
	}
	
	public String scoreInorden() {
		return scoreInorden(root);
	}
	
	public String scoreInorden(User current) {
		String scores = "";
		if(current!=null) {
			scores += scoreInorden(current.getLeft());
			scores += current.getNickname() + " ========> " + current.getScore() + "\n";
			scores += scoreInorden(current.getRight());
		}
		return scores;
	}
	
	public void saveRoot() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_SERIALIZABLE_ROOT));
		oos.writeObject(root);
		oos.close();
	}
	
	public void loadRoot() throws IOException, ClassNotFoundException{	
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_SERIALIZABLE_ROOT));
		root = (User)ois.readObject();
		ois.close();
	}
	
	public int getKGlobal() {
		return kGlobal;
	}
	
	public int getScore() {
		return score;
	}
}
