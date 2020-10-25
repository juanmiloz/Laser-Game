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
		System.out.println(printColumns());
	}
	
	public void createColumns(Box current ,int m) {
		if(m!=0) {
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
