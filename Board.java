/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 * Autor: Cristian Camilo Novoa Avellaneda
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Casilla del juego
 */
public class Board {

	/**
	 * Clase referente a las casillas del tablero.
	 */
	private static class Casilla {
		/**
		 * Indica si no ha sido destapada la casilla.
		 */
		public boolean oculto = true;

		/**
		 * Indica si la casilla es una mina.
		 */
		public boolean mina;

		/**
		 * Indica si la casilla es marcada con una bandera.
		 */
		public boolean marca = false;

		/**
		 * Indica si la casilla fue destapada.
		 */
		public boolean destapo = false;

		/**
		 * Indica si la casilla tiene minas adyacentes.
		 */
		public int adyacentes = 0;

		/**
		 * Inicializa la casilla.
		 * @param mina.
		 */
		public Casilla(boolean nMina) {
			mina = nMina;
		}
	}

	/**
	 * Alto del tablero.
	 */
	private int alto;

	/**
	 * Ancho del tablero.
	 */
	private int ancho;

	/**
	 * Casilla del tablero
	 */
	private Casilla[][] casilla;

	/**
	 * Indica si hay victoria.
	 */
	private boolean victoria = false;

	/**
	 * Casilla c
	 */
	private Casilla c;

	/**
	 * Número de minas en tablero.
	 */
	private int numMinas;
	
	/**
	 * Banderas correctas
	 */
	private int marcadasMina;

	/**
	 * Banderas incorrectas
	 */
	private int marcadasError;

	/**
	 * Número de movimientos/turnos.
	 */
	private int movimientos = 0;


	/**
	 * Inicializar el tablero del juego.
	 * @param alto del tablero.
	 * @param ancho del tablero.
	 * @param minas del tablero.
	 * @throws NumberFormatException the number format exception.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	public Board(int pAlto, int pAncho, int nMinas)throws NumberFormatException, IOException {
		alto = pAlto;
		ancho = pAncho;
		numMinas = nMinas;
		crearTablero(nMinas);
		calcularAdyacentes();
		mostrarTablero();
		do {
			solicitarCoordenadas();
		} while (victoria == false);
	}

	/**
	 * Crear tablero del juego.
	 * @param nMinas del tablero. 
	 */
	
	public void crearTablero(int nMinas) {
		movimientos = 0;
		List<Casilla> minas = new ArrayList<Casilla>();
		for (int i = 0; i < alto * ancho; i++) {
			minas.add(new Casilla(i < nMinas));
		}
		Collections.shuffle(minas);
		casilla = new Casilla[alto + 2][ancho + 2];
		for (int i = 0; i < alto + 2; i++) {
			for (int j = 0; j < ancho + 2; j++) {
				casilla[i][j] = (i == 0 || j == 0 || i == alto + 1 || j == ancho + 1) ? new Casilla(false) : minas.remove(0);
			}
		}
	}

	/**
	 * Solicitar coordenadas de acuerdo a la acción dada.
	 * @throws NumberFormatException the number format exception.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public void solicitarCoordenadas() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if (Minesweeper.espanol) {
			System.out.println("¿Qué desea hacer?");
			System.out.println("a) Destapar");
			System.out.println("b) Poner bandera");
			if (movimientos>=1) {
				System.out.println("c) Salir del juego");
			}
			String accion = br.readLine();

			if (accion.equals("a")) {
				System.out.println("Introduzca las coordenadas (si desea destapar la casilla en la posición 3x4 introduzca '3 4')");
				String[] parts = br.readLine().split(" ");
				int coordenadaY = Integer.parseInt(parts[0]);
				int coordenadaX = Integer.parseInt(parts[1]);
				descubrir(coordenadaY, coordenadaX);
				movimientos++;
			}
			if (accion.equals("b")) {
				System.out.println("Introduzca las coordenadas (si desea poner una bandera en la casilla en la posición 3x4 introduzca '3 4')");
				String[] parts = br.readLine().split(" ");
				int coordenadaY = Integer.parseInt(parts[0]);
				int coordenadaX = Integer.parseInt(parts[1]);
				marcar(coordenadaY, coordenadaX);
				movimientos++;
			}
			mostrarTablero();
			verSiDerrota();
			marcadasMina=0;
			marcadasError=0;
			comprobarVictoria();
			calcularAdyacentes();

			if (accion.equals("c")) {
				victoria=true;
			}

		}
		if (Minesweeper.english) {
			System.out.println("What do you want to do?");
			System.out.println("a) Open box");
			System.out.println("b) Put a flag");
			if (movimientos>=1) {
				System.out.println("c) Quit");
			}
			String accion = br.readLine();

			if (accion.equals("a")) {
				System.out.println("Input coordinates (if you want to open the box in position 3x4 input '3 4')");
				String[] parts = br.readLine().split(" ");
				int coordenadaY = Integer.parseInt(parts[0]);
				int coordenadaX = Integer.parseInt(parts[1]);
				descubrir(coordenadaY, coordenadaX);
				movimientos++;
			}
			if (accion.equals("b")) {
				System.out.println("Input coordinates (if you want to put a flag at the box in position 3x4 input '3 4')");
				String[] parts = br.readLine().split(" ");
				int coordenadaY = Integer.parseInt(parts[0]);
				int coordenadaX = Integer.parseInt(parts[1]);
				marcar(coordenadaY, coordenadaX);
				movimientos++;
			}
			if (accion.equals("c")) {
				victoria=true;
			}
			mostrarTablero();
			verSiDerrota();
			marcadasMina=0;
			marcadasError=0;
			comprobarVictoria();
			calcularAdyacentes();


		}
	}

	/**
	 * Destapa la casilla en la posición.
	 * @param x coordenada.
	 * @param y coordenada.
	 * @return true/false.
	 */

	public boolean descubrir(int x, int y) {
		if (!dentroCasilla(x, y)) {
			return false;
		}
		c = casilla[x][y];

		if (!c.oculto) {
			return c.mina;
		}

		c.oculto = false;
		c.destapo = true;

		if (c.mina) {
			return true;
		}

		if (c.adyacentes > 0) {
			return false;
		}

		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				descubrir(i, j);
			}
		}
		return false;
	}
	
	/**
	 * Marca la casilla en la posición.
	 * @param x coordenada.
	 * @param y coordenada.
	 */

	public void marcar(int x, int y) {
		if (Minesweeper.espanol) {
			if (!dentroCasilla(x, y)) {
				System.err.println("Introduzca coordenadas válidas.");
			}

			c = casilla[x][y];
			if (c.marca==true) {
				c.marca=false;
			} else if (c.destapo==false && c.marca==false) {
				c.marca= true;
			} else {
				System.err.println("La casilla ya está destapada");
			}
		}
		if (Minesweeper.english) {
			if (!dentroCasilla(x, y)) {
				System.err.println("Please, input valid coordinates.");
			}

			c = casilla[x][y];
			if (c.marca==true) {
				c.marca=false;
			} else if (c.destapo==false && c.marca==false) {
				c.marca= true;
			} else {
				System.err.println("The box is open");
			}
		}
	}

	/**
	 * Se usa para saber si las coordenadas están dentro del tablero.
	 * @param x coordenada
	 * @param y coordenada
	 * @return true si está. False si no.
	 */
	public boolean dentroCasilla(int x, int y) {
		if (x > 0 && y > 0 && x <= alto && y <= ancho) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Se comprueba si el jugador perdió y se muestra donde estaban las minas.
	 */
	public void verSiDerrota() {
		if (c.mina && c.destapo) {
			if (Minesweeper.espanol) {
				System.out.println("\nAquí estaban las minas\n");
				for (int i = 1; i <= alto; i++) {
					if (alto>9 && i<10)
						System.out.printf("%d | ", i);
					else
						System.out.printf("%d| ", i);
					for (int j = 1; j <= ancho; j++) {
						Casilla f = casilla[i][j];
						if (f.oculto && f.mina) {
							f.oculto = false;
						}
						if (f.oculto) {
							System.out.print(".  ");
						} else if (f.mina) {
							System.out.print("*  ");
						} else {
							if (f.adyacentes==0)
								System.out.print("-" + "  ");
							else
								System.out.print(f.adyacentes + "  ");
						}
					}
					System.out.println();
				}
				System.out.print("    ");
				for (int j = 1; j <= ancho; j++) {
					if (Integer.toString(j).length()>1) {
						System.out.print(j + " ");
					}
					else {
						System.out.print(j + "  ");
					}
				}
				System.out.println();

				System.out.println("\n    ____                ___      __            ____      __             __               __                                  \n" + 
						"   / __ \\___  _________/ (_)____/ /____       /  _/___  / /____  ____  / /_____ _   ____/ /__     ____  __  _____ _   ______ \n" + 
						"  / /_/ / _ \\/ ___/ __  / / ___/ __/ _ \\      / // __ \\/ __/ _ \\/ __ \\/ __/ __ `/  / __  / _ \\   / __ \\/ / / / _ \\ | / / __ \\\n" + 
						" / ____/  __/ /  / /_/ / (__  ) /_/  __/    _/ // / / / /_/  __/ / / / /_/ /_/ /  / /_/ /  __/  / / / / /_/ /  __/ |/ / /_/ /\n" + 
						"/_/    \\___/_/   \\__,_/_/____/\\__/\\___(_)  /___/_/ /_/\\__/\\___/_/ /_/\\__/\\__,_/   \\__,_/\\___/  /_/ /_/\\__,_/\\___/|___/\\____/ \n" + 
						"                                                                                                                             \n");
				movimientos = 0;
				victoria = true;
			}
			if (Minesweeper.english) {
				System.out.println("\nHere was the mines\n");
				for (int i = 1; i <= alto; i++) {
					System.out.printf("%2d| ", i);
					for (int j = 1; j <= ancho; j++) {
						Casilla f = casilla[i][j];
						if (f.oculto && f.mina) {
							f.oculto = false;
						}
						if (f.oculto) {
							System.out.print(".  ");
						} else if (f.mina) {
							System.out.print("*  ");
						} else {
							System.out.print(f.adyacentes + "  ");
						}
					}
					System.out.println();
				}
				System.out.print("    ");
				for (int j = 1; j <= ancho; j++) {
					System.out.print(j + "  ");
				}
				System.out.println();

				System.out.println("\n__  __               __           __       ______                                _     \n" + 
						"\\ \\/ /___  __  __   / /___  _____/ /_     /_  __/______  __   ____ _____ _____ _(_)___ \n" + 
						" \\  / __ \\/ / / /  / / __ \\/ ___/ __/      / / / ___/ / / /  / __ `/ __ `/ __ `/ / __ \\\n" + 
						" / / /_/ / /_/ /  / / /_/ (__  ) /__      / / / /  / /_/ /  / /_/ / /_/ / /_/ / / / / /\n" + 
						"/_/\\____/\\__,_/  /_/\\____/____/\\__(_)    /_/ /_/   \\__, /   \\__,_/\\__, /\\__,_/_/_/ /_/ \n" + 
						"                                                  /____/         /____/                \n");
				movimientos = 0;
				victoria = true;
			}
		}
	}

	/**
	 * Se comprueba si ya ganó el jugador.
	 * Puede ganar de dos maneras:
	 * 1. Destapó todas las casillas sin mina.
	 * 2. Marcó todas las casillas con mina (y únicamente esas).
	 */
	public void comprobarVictoria() {
		int aux = 0;
		for (int i = 1; i <= alto; i++) {
			for (int j = 1; j <= ancho; j++) {
				c = casilla[i][j];
				if (!c.oculto && !c.mina) {
					aux++;
				}
				if (c.mina && c.marca) {
					marcadasMina++;
				} 
				if (!c.mina && c.marca){
					marcadasError++;
				}
			}
		}
		if (Minesweeper.espanol) {
			if (aux == (alto * ancho) - numMinas || (marcadasMina==numMinas && marcadasError==0)) {
				System.out.println("\nHas ganado en " + movimientos+ " movimientos. Enhorabuena\n");
				victoria = true;
			}
		}
		if (Minesweeper.english) {
			if (aux == (alto * ancho) - numMinas || (marcadasMina==numMinas && marcadasError==0)) {
				System.out.println("\nYou won in " + movimientos+ " movements. Congrats\n");
				victoria = true;
			}
		}
	}

	/**
	 * Se calcula si la casilla tiene minas adyacentes.
	 */
	public void calcularAdyacentes() {
		for (int i = 1; i <= alto; i++) {
			for (int j = 1; j <= ancho; j++) {
				int aux = 0;
				for (int di = -1; di <= 1; di++) {
					for (int dj = -1; dj <= 1; dj++) {
						if (casilla[i + di][j + dj].mina)
							aux++;
					}
				}
				casilla[i][j].adyacentes = aux;
			}
		}
	}

	/**
	 * Se muestra el estado del tablero.
	 */
	public void mostrarTablero() {
		for (int i = 1; i <= alto; i++) {
			if (alto>9 && i<10)
				System.out.printf("%d | ", i);
			else
				System.out.printf("%d| ", i);
			for (int j = 1; j <= ancho; j++) {
				Casilla f = casilla[i][j];
				if (f.marca)
					System.out.print("¶  ");
				if ((f.oculto && f.mina && !f.destapo && !f.marca)||(f.oculto && !f.mina && !f.destapo && !f.marca)) {
					System.out.print(".  ");
				} 
				if (f.mina && !f.oculto && f.destapo) {
					System.out.print("*  ");
				}
				if (!f.oculto && f.destapo){
					if (f.adyacentes==0)
						System.out.print("-" + "  ");
					else
						System.out.print(f.adyacentes + "  ");
				}
			}
			System.out.println();
		}
		if (alto>9)
			System.out.print("    ");
		else
			System.out.print("   ");

		for (int j = 1; j <= ancho; j++) {
			if (Integer.toString(j).length()>1) {
				System.out.print(j + " ");
			}
			else {
				System.out.print(j + "  ");
			}
		}
		System.out.println();
	}
}
