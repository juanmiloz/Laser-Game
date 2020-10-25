package ui;

import java.util.Scanner;
import model.*;

public class Menu {

	static Scanner in = new Scanner(System.in);
	private final static int START_GAME = 1;
	private final static int SHOW_POSITIONS = 2;
	static LaserGame laserGame = new LaserGame();
	
	public void controlatorMenu() {
		recursiveMenu(printMenu());
	}
	
	public int printMenu() {
		int option = 0;
		System.out.println("===========================");
		System.out.println("|Bienvenido al juego láser|");
		System.out.println("===========================");
		System.out.println("Digite la opcion deseada");
		System.out.println("(1)<---Iniciar juego");
		System.out.println("(2)<---Ver tablero de posiciones");
		System.out.println("(3)<---Salir del juego");
		option = Integer.parseInt(in.nextLine());
		return option;
	}
	
	public void recursiveMenu(int option) {
		if(option < 3) {
			switch(option) {
				case START_GAME: 
					startGame();
				break;
				
				case SHOW_POSITIONS: 
					showPositions();
				break;
			}
			controlatorMenu();
		}
	}
	
	public void startGame() {
		System.out.println("Ingrese su nickname, numero de filas, numero de columnas y numero de espejos separados por un espacio");
		String dataGame[] = in.nextLine().split(" ");
		String nickname = dataGame[0];
		int n = Integer.parseInt(dataGame[1]);
		int m = Integer.parseInt(dataGame[2]);
		int k = Integer.parseInt(dataGame[3]);
		laserGame.createPlayArea(nickname, n, m, k);
	}
	
	public void showPositions() {
		System.out.println();
	}
	
	public void startProgram() {
		controlatorMenu();
	}
	
	/*
	public void mainMenu(){
		int option = 0;
		do {
			System.out.println("===========================");
			System.out.println("|Bienvenido al juego láser|");
			System.out.println("===========================");
			System.out.println("Digite la opcion deseada");
			System.out.println("(1)<---Iniciar juego");
			System.out.println("(2)<---Ver tablero de posiciones");
			System.out.println("(3)<---Salir del juego");
			option = Integer.parseInt(in.nextLine());
			switch(option) {
				case START_GAME: 
					startGame();
				break;
				
				case SHOW_POSITIONS: 
					showPositions();
				break;
			}
		}while(option < 3);
	}
	*/
}
