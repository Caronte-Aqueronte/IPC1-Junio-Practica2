package src;

import java.util.Scanner;

public class Practica2 {

    public static void main(String[] args) {
        Practica2 practica = new Practica2();
    }
    Scanner scanner = new Scanner(System.in);

    //estructura para clientes
    String[] nombreCliente = new String[30];
    int[] idCliente = new int[30];
    int[] telefonoCliente = new int[30];
    boolean[] tienePrestado = new boolean[30];
    //

    //estructua para peliculas
    int[] idPelicula = new int[30];
    String[] nombrePelicula = new String[30];
    int[] age = new int[30];
    String[] categoriaPelicula = new String[30];
    boolean[] disponibilidadPelicula = new boolean[30];
    //

    //prestamo de peliculas
    int[][] prestamoPeliculas = new int[30][3];
    //

    /**
     * herramientas de control Estos contadores serviran para dar automaticamnte
     * un id a cliente y pelicula asi aseguramos que no se repita
     */
    int contadorIdCliente = 0;
    int contadorIdPelicula = 0;

    //
    public Practica2() {
        int opcion = 0;
        while (opcion != 9) {
            System.out.println("");
            System.out.println("***\tBIENVENIDO\t***");
            System.out.println("¿Que desea hacer?");
            System.out.println("1.Prestamo de peliculas\n2.Devolucion de peliculas\n3.Mostrar las peliculas\n4.Ingreso de pelicula\n5.Ordenar peliculas A-Z\n6.Ingresar clientes nuevos\n7.Mostrar clientes\n8.Reportes\n9.Salir");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    prestarPeliculas();
                    break;
                case 2:
                    break;
                case 3:
                    mostrarPeliculas();
                    break;
                case 4:
                    crearPelicula();
                    break;
                case 5:
                    break;
                case 6:
                    crearCliente();
                    break;
                case 7:
                    mostrarClientes();
                    break;
                case 8:
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;

            }
        }
    }

    /**
     * En este metodo creamos la pelicula con los paremtros nesecarios
     */
    public void crearPelicula() {
        String nombre;
        int anio;
        String categoria;
        boolean disponible = true;
        contadorIdPelicula++; //aqui obtenemos el nuevo id de la pelicula
        scanner.skip("\n");
        System.out.println("Ingrese nombre de la pelicula");
        nombre = scanner.nextLine();
        System.out.println("Ingrese año de salida");
        anio = scanner.nextInt();
        scanner.skip("\n");
        System.out.println("Ingrse categoria");
        categoria = scanner.nextLine();
        guardarPelicula(nombre, anio, categoria, disponible);
    }

    /**
     * Este metodo guarda la pelicula donde el id sea cero
     *
     * @param nombre
     * @param anio
     * @param categoria
     * @param disponible
     */
    public void guardarPelicula(String nombre, int anio, String categoria, boolean disponible) {
        for (int x = 0; x < idPelicula.length; x++) {
            if (idPelicula[x] == 0) {
                idPelicula[x] = contadorIdPelicula;
                nombrePelicula[x] = nombre;
                age[x] = anio;
                categoriaPelicula[x] = categoria;
                disponibilidadPelicula[x] = disponible;
                System.out.println("Pelicula guardada");
                return;
            }
        }
        System.out.println("Pelicula NO guardada, se alcanzo el maximo de peliculas");
    }

    /**
     * Este metodo muetra las peliculas
     */
    public boolean mostrarPeliculas() {
        if (idPelicula[0] != 0) {
            for (int x = 0; x < idPelicula.length; x++) {
                System.out.println("Id: " + idPelicula[x] + " Nombre: " + nombrePelicula[x] + " Año: " + age[x] + " Categoria: " + categoriaPelicula[x] + " Disponibilidad: " + disponibilidadPelicula[x]);
                if (idPelicula[x + 1] == 0) {
                    return true;
                }
            }
        }
        System.out.println("Aun no hay pelicuas ingresadas");
        return false;
    }

    /**
     * este metodo es para prestar las peliculas
     */
    public void prestarPeliculas() {
        int idPelicula;
        int idCliente;
        boolean banderaExistencias = mostrarPeliculas();
        boolean prestado;
        boolean disponible;
        if (banderaExistencias == true) {

            System.out.println("Ingrese su id de cliente");
            idCliente = scanner.nextInt();
            prestado = estadoCliente(idCliente);

            if (prestado == true) {

                System.out.println("Ingrese el id de la pelicula a su eleccion");
                idPelicula = scanner.nextInt();
                disponible = estadoPelicula(idPelicula);

                if (disponible == true) {
                    cambiarEdadoPelicula(idPelicula, false);
                    cambiarEstadoCliente(idCliente, true);
                } else {
                    System.out.println("Pelicula no disponible");
                }

            } else {

                System.out.println("Usted tiene prestada una pelicula o su id no existe, verifique e intentelo  mas tarde");

            }
        }
    }

    /**
     * Este metodo evalua si el cliente tiene en posecion o no una pelicula
     *
     * @return
     */
    public boolean estadoCliente(int id) {
        for (int x = 0; x < idCliente.length; x++) {
            if (id == idCliente[x]) {
                if (tienePrestado[x] == false) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Este metodo evalua si la pelicula esta disponible
     *
     * @return
     */
    public boolean estadoPelicula(int id) {
        for (int x = 0; x < idPelicula.length; x++) {
            if (id == idPelicula[x]) {
                if (disponibilidadPelicula[x] == true) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Este metodo cambia el estado de cliente
     *
     * @param id
     * @param nuevoEstado
     */
    public void cambiarEstadoCliente(int id, boolean nuevoEstado) {
        for (int x = 0; x < idCliente.length; x++) {
            if (id == idCliente[x]) {
                tienePrestado[x] = nuevoEstado;
            }
        }
    }

    /**
     * Este metodo cambia el estado de pelicula
     *
     * @param id
     * @param nuevoEstado
     */
    public void cambiarEdadoPelicula(int id, boolean nuevoEstado) {
        for (int x = 0; x < idPelicula.length; x++) {
            if (id == idPelicula[x]) {
                disponibilidadPelicula[x] = nuevoEstado;
            }
        }
    }

    /**
     * este metodo pide los datos para crear un cliente
     */
    public void crearCliente() {
        String nombre;
        int telefono;
        boolean tienePrestado = false;
        contadorIdCliente++; //aqui obtenemos el nuevo id de la pelicula
        scanner.skip("\n");
        System.out.println("Ingrese nombre del cliente");
        nombre = scanner.nextLine();
        System.out.println("Ingrese el telefono del cliente");
        telefono = scanner.nextInt();
        guardarCliente(nombre, telefono, tienePrestado);
    }

    /**
     * Este metodo guarda el cliente donde el id sea cero
     *
     * @param nombre
     * @param telefono
     * @param tienePrestado
     *
     */
    public void guardarCliente(String nombre, int telefono, boolean tienePrestado) {
        for (int x = 0; x < idCliente.length; x++) {
            if (idCliente[x] == 0) {
                idCliente[x] = contadorIdCliente;
                nombreCliente[x] = nombre;
                telefonoCliente[x] = telefono;
                this.tienePrestado[x] = tienePrestado;
                System.out.println("Cliente registrado");
                return;
            }
        }
        System.out.println("Cliente NO registrado, se alcanzo el maximo de clientes");
    }
    
     public boolean mostrarClientes() {
        if (idCliente[0] != 0) {
            for (int x = 0; x < idCliente.length; x++) {
                System.out.println("Id: " + idCliente[x] + " Nombre: " + nombreCliente[x] + " Telefono: " + telefonoCliente[x] + " Tiene prestado pelicula: " + tienePrestado[x]);
                if (idCliente[x + 1] == 0) {
                    return true;
                }
            }
        }
        System.out.println("Aun no hay clientes ingresados");
        return false;
    }
}