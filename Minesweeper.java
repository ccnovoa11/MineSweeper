/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 * Autor: Cristian Camilo Novoa Avellaneda
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Minesweeper {

	/**
	 * Constante para determinar si el juego se inicializa en español.
	 */
	public static boolean espanol = false;

	/**
	 * Constante para determinar si el juego se inicializa en inglés.
	 */
	public static boolean english = false;

	/**
	 * Main para inicializar.
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean corriendo = true;
		boolean corriendo2 = true;
		boolean corriendo3 = true;
		boolean menuTradicional = true;
		boolean lenguaje = true;
		boolean partidaMenu = true;	
		int alto = 0;	
		int ancho = 0;
		int numeroMinas = 0;

		// Menú para selección de idioma.
		while (corriendo3) {
			System.out.println("Seleccione el idioma/Select yout language");
			System.out.println("a) Español");
			System.out.println("b) English");
			String idioma = br.readLine();

			do {
				// Menu en español
				if (idioma.equals("a")) {
					espanol = true;
					while (corriendo) {
						System.out.println(".______    __    __       _______.  ______     ___      .___  ___.  __  .__   __.      ___           _______.\n" + 
								"|   _  \\  |  |  |  |     /       | /      |   /   \\     |   \\/   | |  | |  \\ |  |     /   \\         /       |\n" + 
								"|  |_)  | |  |  |  |    |   (----`|  ,----'  /  ^  \\    |  \\  /  | |  | |   \\|  |    /  ^  \\       |   (----`\n" + 
								"|   _  <  |  |  |  |     \\   \\    |  |      /  /_\\  \\   |  |\\/|  | |  | |  . `  |   /  /_\\  \\       \\   \\    \n" + 
								"|  |_)  | |  `--'  | .----)   |   |  `----./  _____  \\  |  |  |  | |  | |  |\\   |  /  _____  \\  .----)   |   \n" + 
								"|______/   \\______/  |_______/     \\______/__/     \\__\\ |__|  |__| |__| |__| \\__| /__/     \\__\\ |_______/    \n" + 
								"                                                                                                             ");
						corriendo2 = true;
						System.out.println("Escoja una opción (a ó b)");
						System.out.println("a) Empezar partida");
						System.out.println("b) Salir");

						String linea = br.readLine();

						do {
							// Modos de juego (tradicional o personalizado)
							if (linea.equals("a")) {
								System.out.println("Escoja el estilo de juego (a ó b, c para volver)");
								System.out.println("a) Tradicional");
								System.out.println("b) Personalizado");
								System.out.println("c) Volver");
								String lineaPartida = br.readLine();
								do {
									if (lineaPartida.equals("a")) {
										do {
											System.out.println("Escoja una de las siguientes opciones (a, b, c ó d)");
											System.out.println("a) Principiante: Tablero de 9x9 con 10 minas.");
											System.out.println("b) Intermedio: Tablero de 16x16 y 40 minas.");
											System.out.println("c) Experto: Tablero de 16x30 y 99 minas.");
											System.out.println("d) Volver.");
											String opcion = br.readLine();
											if (opcion.equals("a")) {
												Board juego = new Board(9, 9, 10);
											}
											if (opcion.equals("b")) {
												Board juego = new Board(16, 16, 40);
											}
											if (opcion.equals("c")) {
												Board juego = new Board(16, 30, 99);
											}

											if (opcion.equals("d")) {
												menuTradicional=false;
											}

										} while (menuTradicional==true);
									}
									if (lineaPartida.equals("b")) {
										// Solicitar alto y ancho.
										System.out.println("Introduzca las coordenadas (p.ej. si desea un tablero de alto 15 y ancho 20 introduzca '15 20')");
										String[] parts = br.readLine().split(" ");

										if (parts.length<= 1 || parts.length>2) {
											System.err.println("Ha ocurrido un error en la lectura de sus coordenadas\n");
											break;
										}

										alto = Integer.parseInt(parts[0]);
										ancho = Integer.parseInt(parts[1]);

										System.out.println("Introduzca el número de minas");
										numeroMinas = (Integer.parseInt(br.readLine()));
										if (numeroMinas >= alto*ancho) {
											System.err.println("El número de minas debe ser menor al número de casillas\n");
											break;
										}
										Board juego = new Board(alto, ancho, numeroMinas);

									}
									if (lineaPartida.equals("c")) {
										// Volver al menú anterior.
										partidaMenu = false;
										corriendo2 = false;
									}
									partidaMenu = false;
								} while (partidaMenu);

							}

							if (linea.equals("b")) {
								// Salir del programa
								corriendo3 = false;
								lenguaje = false;
								corriendo = false;
								corriendo2 = false;
								System.out.println(" _____              _          __                            _                                                          \n" + 
										"|  _  |            | |        / _|                          | |                                              ____       \n" + 
										"| | | |_   _  ___  | | __ _  | |_ _   _  ___ _ __ ______ _  | |_ ___    __ _  ___ ___  _ __ ___  _ __   __ _ _ __   ___ \n" + 
										"| | | | | | |/ _ \\ | |/ _` | |  _| | | |/ _ \\ '__|_  / _` | | __/ _ \\  / _` |/ __/ _ \\| '_ ` _ \\| '_ \\ / _` | '_ \\ / _ \\\n" + 
										"\\ \\/' / |_| |  __/ | | (_| | | | | |_| |  __/ |   / / (_| | | ||  __/ | (_| | (_| (_) | | | | | | |_) | (_| | | | |  __/\n" + 
										" \\_/\\_\\\\__,_|\\___| |_|\\__,_| |_|  \\__,_|\\___|_|  /___\\__,_|  \\__\\___|  \\__,_|\\___\\___/|_| |_| |_| .__/ \\__,_|_| |_|\\___|\n" + 
										"                                                                                                | |                     \n" + 
										"                                                                                                |_|                     ");
							}

							// 
							if (!(linea.equals("a") || linea.equals("b"))) {
								System.err.println("Error. Por favor, introduce a ó b");
								try {
									Thread.sleep(2000);
								} catch (InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
								corriendo2 = false;
							}
						} while (corriendo2);
					}
				}
				if (idioma.equals("b")) {
					english = true;
					while (corriendo) {
						System.out.println(".___  ___.  __  .__   __.  _______     _______.____    __    ____  _______  _______ .______    _______ .______      ");
						System.out.println("|   \\/   | |  | |  \\ |  | |   ____|   /       |\\   \\  /  \\  /   / |   ____||   ____||   _  \\  |   ____||   _  \\     ");
						System.out.println("|  \\  /  | |  | |   \\|  | |  |__     |   (----` \\   \\/    \\/   /  |  |__   |  |__   |  |_)  | |  |__   |  |_)  |    ");
						System.out.println("|  |\\/|  | |  | |  . `  | |   __|     \\   \\      \\            /   |   __|  |   __|  |   ___/  |   __|  |      /     ");
						System.out.println("|  |  |  | |  | |  |\\   | |  |____.----)   |      \\    /\\    /    |  |____ |  |____ |  |      |  |____ |  |\\  \\----.");
						System.out.println("|__|  |__| |__| |__| \\__| |_______|_______/        \\__/  \\__/     |_______||_______|| _|      |_______|| _| `._____|");
						System.out.println("                                                                                                                    ");
						corriendo2 = true;
						System.out.println("Select an option (a or b)");
						System.out.println("a) Start to play");
						System.out.println("b) Quit");

						String linea = br.readLine();

						do {
							// Tableros por defecto o personalizado
							if (linea.equals("a")) {
								System.out.println("Choose a style game (a or b, c to comeback)");
								System.out.println("a) Traditional");
								System.out.println("b) Custom");
								System.out.println("c) Return to menu");
								String lineaPartida = br.readLine();
								do {
									if (lineaPartida.equals("a")) {
										do {
											System.out.println("Choose between this options (a, b, c ó d)");
											System.out.println("a) Beginner: 9x9 Board with 10 mines.");
											System.out.println("b) Intermediate: 16x16 Board with 40 mines.");
											System.out.println("c) Expert: 16x30 Board with 99 mines.");
											System.out.println("d) Return to menu.");
											String opcion = br.readLine();
											if (opcion.equals("a")) {
												Board juego = new Board(9, 9, 10);
											}
											if (opcion.equals("b")) {
												Board juego = new Board(16, 16, 40);
											}
											if (opcion.equals("c")) {
												Board juego = new Board(16, 30, 99);
											}

											if (opcion.equals("d")) {
												menuTradicional=false;
											}

										} while (menuTradicional==true);
									}
									if (lineaPartida.equals("b")) {
										System.out.println("Input the coordinates (e.g. if you want a 15x20 board, input '15 20')");
										String[] parts = br.readLine().split(" ");

										if (parts.length<= 1 || parts.length>2) {
											System.err.println("An error occurred. Please try again.\n");
											break;
										}

										alto = Integer.parseInt(parts[0]);
										ancho = Integer.parseInt(parts[1]);

										System.out.println("Input mines' number");
										numeroMinas = (Integer.parseInt(br.readLine()));
										if (numeroMinas >= alto*ancho) {
											System.err.println("Mines' number must be lower than boxes \n");
											break;
										}
										Board juego = new Board(alto, ancho, numeroMinas);

									}
									if (lineaPartida.equals("c")) {
										partidaMenu = false;
										corriendo2 = false;
									}
									partidaMenu = false;
								} while (partidaMenu);

							}

							if (linea.equals("b")) {
								corriendo3 = false;
								lenguaje = false;
								corriendo = false;
								corriendo2 = false;
								System.out.println("___  ___              _   _             __                     _                     _ _   _                         \n" + 
										"|  \\/  |             | | | |           / _|                   | |                   (_) | | |                        \n" + 
										"| .  . | __ _ _   _  | |_| |__   ___  | |_ ___  _ __ ___ ___  | |__   ___  __      ___| |_| |__    _   _  ___  _   _ \n" + 
										"| |\\/| |/ _` | | | | | __| '_ \\ / _ \\ |  _/ _ \\| '__/ __/ _ \\ | '_ \\ / _ \\ \\ \\ /\\ / / | __| '_ \\  | | | |/ _ \\| | | |\n" + 
										"| |  | | (_| | |_| | | |_| | | |  __/ | || (_) | | | (_|  __/ | |_) |  __/  \\ V  V /| | |_| | | | | |_| | (_) | |_| |\n" + 
										"\\_|  |_/\\__,_|\\__, |  \\__|_| |_|\\___| |_| \\___/|_|  \\___\\___| |_.__/ \\___|   \\_/\\_/ |_|\\__|_| |_|  \\__, |\\___/ \\__,_|\n" + 
										"               __/ |                                                                                __/ |            \n" + 
										"              |___/                                                                                |___/             ");
							}

							
							if (!(linea.equals("a") || linea.equals("b"))) {
								System.err.println("Error. Please input a or b");
								try {
									Thread.sleep(2000);
								} catch (InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
								corriendo2 = false;
							}
						} while (corriendo2);
					}
				}
				if (!(idioma.equals("a") || idioma.equals("b"))) {
					System.err.println("Error. Por favor, selecciona el idioma.");
					System.err.println("Please, select your language.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
					lenguaje = false;
				}
			} while (lenguaje == true);
		}
	}
}
