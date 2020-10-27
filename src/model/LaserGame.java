package model;

public class LaserGame {

	User root;
	Box first;
	int kGlobal; 
	
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
		
		return area; 
	}
	
	public String startGame(String input, String nickname) {
		String answer = "";
		if(input.charAt(0)=='L') {
			int n = (int)(input.charAt(1));
			int m = (int)(input.charAt(2));
			n = n-49+1;
			m = m-65+1;
			String direction = String.valueOf(input.charAt(3));
			String temp = locateMirror(n, m, direction);
			answer += nickname + "  |  " + kGlobal + " espejos restantes por encontrar";
			answer += printAreaGame(first)+"\n";
			answer += temp;
		}else {
			
		}
		return answer;
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
		}else if(searchBox.getMirror() != null && searchBox.getMirror().equalsIgnoreCase(direction)) {
			searchBox.setVisibility(true);
			answer += "Se encontro un espejo";
			kGlobal -= 1;
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
	
	public int getKGlobal() {
		return kGlobal;
	}
}
