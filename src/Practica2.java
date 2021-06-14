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
    int[] vecesPrestada = new int[30];
    //

    //prestamo de peliculas
    int[][] prestamoPeliculas = new int[30][3];
    //

    /**
     * herramientas de control Estos contadores serviran para dar automaticamnte
     * un id a cliente y pelicula asi aseguramos que no se repita, ademas en la
     * matriz creada pondremos definidas las categorias de las peliculas
     */
    int contadorIdCliente = 0;
    int contadorIdPelicula = 0;
    String[][] categorias = {{"Comedia", "0"}, {"Accion", "0"}, {"Terror", "0"}, {"Ficcion", "0"}, {"Infantil", "0"}};

    //
    public Practica2() {
        String opcion = "";
        while (!opcion.equals("9")) {
            System.out.println("");
            System.out.println("***\tBIENVENIDO\t***");
            System.out.println("¿Que desea hacer?");
            System.out.println("1.Prestamo de peliculas\n2.Devolucion de peliculas\n3.Mostrar las peliculas\n4.Ingreso de pelicula\n5.Ordenar peliculas A-Z\n6.Ingresar clientes nuevos\n7.Mostrar clientes\n8.Reportes\n9.Salir");
            opcion = scanner.next();
            switch (opcion) {
                case "1":
                    prestarPeliculas();
                    break;
                case "2":
                    devolverPeliculas();
                    break;
                case "3":
                    mostrarPeliculas();
                    break;
                case "4":
                    crearPelicula();
                    break;
                case "5":
                    ordenarPeliculas();
                    break;
                case "6":
                    crearCliente();
                    break;
                case "7":
                    mostrarClientes();
                    break;
                case "8":
                    menuReportes();
                    break;
                case "9":
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
        boolean banderaCategoria = false;
        boolean disponible = true;
        contadorIdPelicula++; //aqui obtenemos el nuevo id de la pelicula
        scanner.skip("\n");
        System.out.println("Ingrese nombre de la pelicula");
        nombre = scanner.nextLine();
        System.out.println("Ingrese año de salida");
        anio = scanner.nextInt();
        scanner.skip("\n");
        categoria = pedirCategoria();
        guardarPelicula(nombre, anio, categoria, disponible);
        sumarCategoria(categoria);
    }

    /**
     * Este metodo suma la cantidad que una categoria posse de peliculas
     * registradas
     *
     * @param categoria
     */
    public void sumarCategoria(String categoria) {
        int cantidad;
        for (int x = 0; x < categorias.length; x++) {
            if (categorias[x][0].equals(categoria)) {
                cantidad = Integer.valueOf(categorias[x][1]);
                cantidad++;
                categorias[x][1] = String.valueOf(cantidad);
            }
        }
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
     *
     * @return
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
        int dias;
        boolean prestado;
        boolean disponible;
        boolean banderaExistencias = mostrarPeliculas();
        if (banderaExistencias == true) {

            System.out.println("Ingrese su id de cliente");
            idCliente = scanner.nextInt();
            prestado = estadoCliente(idCliente);

            if (prestado == false) {

                System.out.println("Ingrese el id de la pelicula a su eleccion");
                idPelicula = scanner.nextInt();
                disponible = estadoPelicula(idPelicula);

                if (disponible == true) {
                    System.out.println("Ingrese los dias que la prestara");
                    dias = scanner.nextInt();
                    cambiarEdadoPelicula(idPelicula, false);
                    cambiarEstadoCliente(idCliente, true);
                    sumarVecesPrestadaUnaPelicula(idPelicula);
                    llenarMatriz(idPelicula, idCliente, dias);
                    System.out.println("Pelicula prestada");
                } else {
                    System.out.println("Pelicula no disponible");
                }

            } else {

                System.out.println("Usted tiene prestada una pelicula o su id no existe, verifique e intentelo  mas tarde");

            }
        }
    }

    /**
     * este metodo recibe un id y lo compara para luego sumar las veces que a
     * sido prestada
     *
     * @param id
     */
    public void sumarVecesPrestadaUnaPelicula(int id) {
        for (int x = 0; x < idPelicula.length; x++) {
            if (idPelicula[x] == id) {
                vecesPrestada[x]++;
            }
        }
    }

    /**
     * Este metodo evalua si el cliente tiene en posecion o no una pelicula
     *
     * @param id
     * @return
     */
    public boolean estadoCliente(int id) {
        for (int x = 0; x < idCliente.length; x++) {
            if (id == idCliente[x]) {
                if (tienePrestado[x] == false) {
                    return false;
                }
                break;
            }
        }
        return true;
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
                break;
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
                break;
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
                break;
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

    /**
     * Este metodo retorna un boolean que puede ser utilizado o no muestra los
     * clientes en caso hayan registrados
     *
     * @return
     */
    public boolean mostrarClientes() {
        if (idCliente[0] != 0) {
            for (int x = 0; x < idCliente.length; x++) {
                System.out.println("Id: " + idCliente[x] + " Nombre: " + nombreCliente[x] + " Telefono: " + telefonoCliente[x] + " Tiene prestado pelicula: " + tienePrestado[x]);
                if (idCliente[x + 1] == 0) {
                    return true;
                }
            }
        }
        System.out.println("Aun no hay clientes registrados");
        return false;
    }

    //este estde metodo aplicamos el metodo burbuja para ordenar
    public void ordenarPeliculas() {

        int i;
        int j;
        String auxString;
        int auxInt;
        boolean auxBoolean;
        for (i = 0; i < nombrePelicula.length - 1; i++) {
            for (j = 0; j < nombrePelicula.length - i - 1; j++) {
                if (nombrePelicula[j + 1] != null && nombrePelicula[j] != null && nombrePelicula[j + 1].compareTo(nombrePelicula[j]) < 0) {
                    //aqui cambiamos para el nombre de la pelicula
                    auxString = nombrePelicula[j + 1];
                    nombrePelicula[j + 1] = nombrePelicula[j];
                    nombrePelicula[j] = auxString;
                    //
                    //aqui cambiamos para el id de la pelicula
                    auxInt = idPelicula[j + 1];
                    idPelicula[j + 1] = idPelicula[j];
                    idPelicula[j] = auxInt;
                    //
                    //aqui cambiamos para el age 
                    auxInt = age[j + 1];
                    age[j + 1] = age[j];
                    age[j] = auxInt;
                    //
                    //aqui cambiamos la categoria
                    auxString = categoriaPelicula[j + 1];
                    categoriaPelicula[j + 1] = categoriaPelicula[j];
                    categoriaPelicula[j] = auxString;
                    //
                    //aqui cambiamos la disponibilidad
                    auxBoolean = disponibilidadPelicula[j + 1];
                    disponibilidadPelicula[j + 1] = disponibilidadPelicula[j];
                    disponibilidadPelicula[j] = auxBoolean;
                    //
                    //aqui cambiamos las veces que han sido prestadas
                    auxInt = vecesPrestada[j + 1];
                    vecesPrestada[j + 1] = vecesPrestada[j];
                    vecesPrestada[j] = auxInt;

                }
            }
        }
        mostrarPeliculas();
    }

    //este metodo llena cada fila de la matriz con el id de la elicula, id del cliente que la tiene, y los dias que esta prestada
    public void llenarMatriz(int idPeli, int idCliente, int dias) {
        int y = 0;
        for (int x = 0; x < prestamoPeliculas.length; x++) {

            if (prestamoPeliculas[x][y] == 0) {
                prestamoPeliculas[x][y] = idPeli;
                prestamoPeliculas[x][y + 1] = idCliente;
                prestamoPeliculas[x][y + 2] = dias;
                return;
            }
        }
    }

    //mostramos las peliculas prestadas
    public boolean mostrarPrestados() {
        String nombrePelicula;
        String nombreCliente;
        int y = 0;
        if (prestamoPeliculas[0][0] != 0) {
            for (int x = 0; x < prestamoPeliculas.length; x++) {
                nombrePelicula = buscarNombrePelicula(prestamoPeliculas[x][y]);
                nombreCliente = buscarNombreCliente(prestamoPeliculas[x][y + 1]);
                System.out.println("Id de pelicula: " + prestamoPeliculas[x][y] + " Nombre pelicula: " + nombrePelicula + " Nombre Cliente: " + nombreCliente);
                if (prestamoPeliculas[x + 1][y] == 0) {
                    return true;
                }
            }
        }
        System.out.println("No hay peliculas prestadas");
        return false;
    }

    //en este metodo tomamos en cuenta el id de la pelicula y retonrnamos su nombre
    public String buscarNombrePelicula(int id) {
        for (int x = 0; x < idPelicula.length; x++) {
            if (id == idPelicula[x]) {
                return nombrePelicula[x];
            }
        }
        return "null";
    }

    //tomamos en cuenta el id del cliente y retonramos su nombre
    public String buscarNombreCliente(int id) {
        for (int x = 0; x < idCliente.length; x++) {
            if (id == idCliente[x]) {
                return nombreCliente[x];
            }
        }
        return "null";
    }

    //tomamos en cuenta el id de la pelicua y retonramos el id del cliente
    public int buscarIdCliente(int id) {
        for (int x = 0; x < prestamoPeliculas.length; x++) {
            if (prestamoPeliculas[x][0] == id) {
                return prestamoPeliculas[x][1];
            }
        }
        return 0;
    }

    //devolver peliculas
    public void devolverPeliculas() {
        int idPeli;
        int idCliente;
        if (mostrarPrestados() != false) {
            do {
                System.out.println("Ingrese id valido de pelicula a devolver");
                idPeli = scanner.nextInt();
                idCliente = buscarIdCliente(idPeli);
            } while (verificarYEliminarPeliculaPrestada(idPeli) != true);
            cambiarEdadoPelicula(idPeli, true);
            cambiarEstadoCliente(idCliente, false);
            System.out.println("Pelicula devuelta");

        }
    }

    //verificar si peliculaprestada existe
    public boolean verificarYEliminarPeliculaPrestada(int id) {
        for (int x = 0; x < prestamoPeliculas.length; x++) {
            if (prestamoPeliculas[x][0] == id) {
                prestamoPeliculas[x][0] = 0;
                prestamoPeliculas[x][1] = 0;
                prestamoPeliculas[x][2] = 0;
                return true;
            }
        }
        return false;
    }

    //este s el menu de los reportes
    public void menuReportes() {
        String opcion = "";
        String categoria = "";
        while (!opcion.equals("6")) {
            System.out.println("");
            System.out.println("***\tBIENVENIDO\t***");
            System.out.println("¿Que tipo de reporte desea ver?");
            System.out.println("1.Cantidad de peliculas por categoria\n2.Peliculas de categoria en especifico\n3.Películas y la cantidad de veces que se presta\n4.Pelicula mas prestada\n5.Pelicula menos prestada\n6.Regresar");
            opcion = scanner.next();
            switch (opcion) {
                case "1":
                    imprimirCategorias();
                    break;
                case "2":
                    categoria = pedirCategoria();
                    imprimirPeliculasPorCategoria(categoria);
                    break;
                case "3":
                    mostrarPeliculaYvecesPrestada();
                    break;
                case "4":
                    saberQuePeliculaEsLaMasPrestada();
                    break;
                case "5":
                    saberQuePeliculaEsLaMenosPrestada();
                    break;
                case "6":
                    break;
                default:
                    System.err.println("Opcion invalida");
                    break;
            }
        }
    }

    //en este metodo imprimimos las categorias con las veces que la prestaron
    public void imprimirCategorias() {
        for (int x = 0; x < categorias.length; x++) {
            System.out.println("La categoria " + categorias[x][0] + " tiene " + categorias[x][1] + " peliculas registradas");
        }
    }

    //imprimir peliculas de cierta categoria
    public void imprimirPeliculasPorCategoria(String categoria) {
        boolean banderaHayExistencias = false;
        if (idPelicula[0] != 0) {
            for (int x = 0; x < categoriaPelicula.length; x++) {
                if (categoriaPelicula[x] != null && categoriaPelicula[x].equals(categoria)) {
                    banderaHayExistencias = true;
                    System.out.println("Id: " + idPelicula[x] + " Nombre: " + nombrePelicula[x] + " Año: " + age[x] + " Categoria: " + categoriaPelicula[x] + " Disponibilidad: " + disponibilidadPelicula[x]);
                    if (idPelicula[x + 1] == 0) {
                        return;
                    }
                }
            }
            if (banderaHayExistencias == false) {
                System.out.println("Esta categoria no tiene peliculas en existencia");
            }
        } else {
            System.out.println("Aun no hay pelicuas ingresadas");
        }
    }

    //pedir categoria
    public String pedirCategoria() {
        String categoria = "";
        boolean banderaCategoria = false;
        while (banderaCategoria == false) {
            System.out.println("Ingrese categoria\n1.Comedia 2.Accion 3.Terror 4.Ficcion 5.Infantil");
            categoria = scanner.next();
            switch (categoria) {
                case "1":
                    categoria = "Comedia";
                    banderaCategoria = true;
                    break;
                case "2":
                    categoria = "Accion";
                    banderaCategoria = true;
                    break;
                case "3":
                    categoria = "Terror";
                    banderaCategoria = true;
                    break;
                case "4":
                    categoria = "Ficcion";
                    banderaCategoria = true;
                    break;
                case "5":
                    categoria = "Infantil";
                    banderaCategoria = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    banderaCategoria = false;
            }
        }
        return categoria;
    }

    //este metodo muestra las peliculas pero con las veces que a sido prestada
    public void mostrarPeliculaYvecesPrestada() {
        if (idPelicula[0] != 0) {
            for (int x = 0; x < idPelicula.length; x++) {
                System.out.println("Id: " + idPelicula[x] + " Nombre: " + nombrePelicula[x] + " Año: " + age[x] + " Categoria: " + categoriaPelicula[x] + " Disponibilidad: " + disponibilidadPelicula[x] + " Se a prestado: " + vecesPrestada[x] + " veces");
                if (idPelicula[x + 1] == 0) {
                    return;
                }
            }
        } else {
            System.out.println("Aun no hay pelicuas ingresadas");
        }
    }

    //aqui evaluamos la pelicula mas prestada
    public void saberQuePeliculaEsLaMasPrestada() {
        int max = 0;
        int pos = 0;
        if (idPelicula[0] != 0) {
            for (int x = 0; x < vecesPrestada.length; x++) {
                if (vecesPrestada[x] > max) {
                    max = vecesPrestada[x];
                    pos = x;
                }
            }
            System.out.println("La pelicula mas prestada es: " + nombrePelicula[pos] + ", La han prestado: " + max + " veces");
        } else {
            System.out.println("Aun no se han registrado peliculas");
        }

    }

    //saber que pelicula es la menos prstada
    public void saberQuePeliculaEsLaMenosPrestada() {
        int min;
        int pos = 0;
        if (idPelicula[0] != 0) {
            min = vecesPrestada[0];
            for (int x = 0; x < vecesPrestada.length; x++) {
                if (idPelicula[x] != 0 && vecesPrestada[x] < min) {
                    min = vecesPrestada[x];
                    pos = x;
                }
            }
            System.out.println("La pelicula menos prestada es: " + nombrePelicula[pos] + ", La han prestado: " + min + " veces");
        } else {
            System.out.println("Aun no se han registrado peliculas");
        }

    }
}
