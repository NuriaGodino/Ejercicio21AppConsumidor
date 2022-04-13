package vista;

import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.negocio.GestorCoche;

public class MainCoche {

	public static void main(String[] args) {

		System.out.println("Bienvenidos a nuestra CRUD de coches");
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		GestorCoche gc = new GestorCoche();
		boolean iden = false;

		for (int i = 2; i >= 0 ; i--) {
			System.out.println("Introduzca su usuario");
			String user = sc.next();
			System.out.println("Introduzca su contraseña");
			String pass = sc.next();
			iden = gc.login(user, pass);

			if (iden == false) {
				System.out.println("El usuario y/o la contraseña son incorrectos");
				System.out.println("Te quedan " + i + " intentos");
			}else {
				i = 0;
			}
		}

		if (iden == true) {
			do {
				menu();
				int opcion = sc.nextInt();
				switch (opcion) {
				case 1:
					System.out.println("Introduce los datos del coche (matricula/marca/modelo/kilometros)");
					String matricula = sc.next();
					String marca = sc.next();
					String modelo = sc.next();
					int km = sc.nextInt();
					Coche c = new Coche();
					c.setMatricula(matricula);
					c.setMarca(marca);
					c.setModelo(modelo);
					c.setKilometros(km);

					int alta = gc.alta(c);
					if (alta == 0) {
						System.out.println("Coche dado de alta");
					} else if (alta == 1) {
						System.out.println("Error de conexion con la BBDD");
					} else if (alta == 2) {
						System.out.println("La matricula tiene menos de 7 caracteres");
					} else if (alta == 3) {
						System.out.println("Los kilometros no pueden ser negativos");
					} else if (alta == 4) {
						System.out.println("La matricula introducida ya esta en la BBDD");
					}

					break;

				case 2:
					System.out.println("Introduce el ID del coche que deseas dar de baja");
					int id = sc.nextInt();
					boolean baja = gc.baja(id);
					if (baja) {
						System.out.println("Coche dado de baja");
					} else {
						System.out.println("Error al dar de baja el coche");
					}
					break;

				case 3:
					System.out.println("Introduce los datos del coche (matricula/marca/modelo/kilometros, id) ");
					matricula = sc.next();
					marca = sc.next();
					modelo = sc.next();
					km = sc.nextInt();
					id = sc.nextInt();

					c = new Coche();
					c.setMatricula(matricula);
					c.setMarca(marca);
					c.setModelo(modelo);
					c.setKilometros(km);
					c.setId(id);

					int mod = gc.modificar(c);

					if (mod == 0) {
						System.out.println("Coche modificado");
					} else if (mod == 1) {
						System.out.println("Error de conexion con la BBDD");
					} else if (mod == 2) {
						System.out.println("La matricula tiene menos de 7 caracteres");
					} else if (mod == 3) {
						System.out.println("Los kilometros no pueden ser negativos");
					} else if (mod == 4) {
						System.out.println("La matricula introducida ya esta en la BBDD");
					}

					break;

				case 4:
					System.out.println("Introduce el ID del coche a buscar");

					id = sc.nextInt();
					c = new Coche();
					c = gc.buscarPorID(id);
					System.out.println("El coche buscado es: " + c);
					break;

				case 5:
					System.out.println("Introduce la matricula del coche a buscar");

					matricula = sc.next();
					c = new Coche();
					c = gc.buscarPorMatricula(matricula);
					System.out.println("El coche buscado es: " + c);
					break;

				case 6:
					System.out.println("Introduce la marca del coche a buscar");

					marca = sc.next();
					c = new Coche();
					c = gc.buscarCochePorMarca(marca);
					System.out.println("El coche buscado es: " + c);
					break;

				case 7:
					System.out.println("Introduce el modelo del coche a buscar");

					modelo = sc.next();
					c = new Coche();
					c = gc.buscarCochePorModelo(modelo);
					System.out.println("El coche buscado es: " + c);
					break;

				case 8:
					System.out.println(gc.listar());
					break;

				case 9:
					gc.exportJSON();
					break;

				case 10:
					gc.exportPDF();
					break;

				case 0:
					fin = true;
					break;
				}
			} while (!fin);

			System.out.println("Fin del programa");
		}

		sc.close();
	}

	private static void menu() {
		System.out.println("Elija una opción: ");
		System.out.println("1- Alta de coche");
		System.out.println("2- Baja de coche");
		System.out.println("3- Modificar coche");
		System.out.println("4- Buscar coche por ID");
		System.out.println("5- Buscar coche por matricula");
		System.out.println("6- Buscar coche por marca");
		System.out.println("7- Buscar coche por modelo");
		System.out.println("8- Listar coches");
		System.out.println("9- Guardar en formato JSON");
		System.out.println("10- Guardar en formato PDF");
		System.out.println("0- Salir");
	}

}
