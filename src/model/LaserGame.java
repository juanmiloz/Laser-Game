package model;

public class LaserGame {

	User root;
	Box first;
	
	public LaserGame() {
		
	}
	
	public void createPlayArea(String nickname, int n, int m, int k) {
		first = new Box(1, 1);
		Box current = first;
		createColumns(current, m-1);
		createAllRows(current,n-1);
		createConectionManagement(current);
		
		System.out.println(printAreaGame(current));
	}
	
	public void startGame(String input) {
		
	}
	
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
	
	public String printAreaGame(Box current) {
		String answer = "";
		if(current != null) {
			answer += "[ ]";
			answer += printRows(current);
			answer += printAreaGame(current.getDown());
		}
		return answer;
	}
	
	public String printRows(Box current) {
		String answer = "";
		if(current.getRight()!= null) {
			answer += "[ ]";
			answer += printRows(current.getRight());
		}else {
			answer += "\n";
		}
		return answer;
	}
	
		
	//imprimir con ciclo por ahora
	public String printColumns() {
		boolean exit = false;
		String print = "";
		Box current = first;
		while(!exit) {
			if(current != null) {
				print += current.getNumRow() +"."+ current.getNumColumn() + " ";
				Box otherCurrent = current.getDown();
				while(otherCurrent != null) {
					print += otherCurrent.getNumRow() + "." + otherCurrent.getNumColumn() + " "; 
					otherCurrent = otherCurrent.getDown();
				}
				print += "\n";
				current = current.getRight();
			}else {
				exit = true;
			}
		}
		return print; 
	}
}
