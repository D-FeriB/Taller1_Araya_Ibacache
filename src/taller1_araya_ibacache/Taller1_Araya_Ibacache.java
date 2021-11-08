/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1_araya_ibacache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author araya_ibacache
 */
public class Taller1_Araya_Ibacache {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        SistemaLigoOfLeyen sys = new SistemaLigoOfLeyenImpl();
        lectura(sys);
        menu(sys);
    }

    public static void lectura(SistemaLigoOfLeyen sys) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("Personajes.txt"));
        while(scan.hasNext()){
            String linea = scan.nextLine();
            String [] partes = linea.split(",");
            String nombreP = partes[0];
            String rolP = partes[1];
            int cantSkins = Integer.parseInt(partes[2]);
            if (!sys.añadirPersonaje(nombreP, rolP, cantSkins)){
                System.out.println("El personaje ya existe");
                break;
            }
            for (int i = 3; i < partes.length; i+=2) {
                String nombreS = partes[i];
                String calidadS = partes[i+1];
                if (!sys.añadirSkin(nombreS, calidadS)){
                    System.out.println("La skin ya existe");
                    break;
                }
                try {
                    sys.asociarPersonajeSkin(nombreP, nombreS);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        Scanner scan1 = new Scanner(new File("Estadisticas.txt"));
        while(scan1.hasNext()){
            String linea = scan1.nextLine();
            String [] partes = linea.split(",");
            String nombreP = partes[0];
            int rec = Integer.parseInt(partes[1]);
            try {
                sys.asociarRecaudacionPersonaje(nombreP, rec);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Scanner scan2 = new Scanner(new File("Cuentas.txt"));
        while(scan2.hasNext()){
            String linea = scan2.nextLine();
            String [] partes = linea.split(",");
            String nombreC = partes[0];
            String contra = partes[1];
            String nick = partes[2];
            int nivel = Integer.parseInt(partes[3]);
            int rp = Integer.parseInt(partes[4]);
            int cant = Integer.parseInt(partes[5]);
            String servidor = partes[partes.length-1];
            sys.añadirCliente(nombreC, contra, nick, nivel, rp, cant, servidor);
            for (int i = 6; i < partes.length-2; i++) {
                String nombreP = partes[i];
                try {
                    sys.asociarClientePersonaje(nombreP, nombreC);
                } catch (Exception e) {
                    System.out.println(e);
                }
                int cantSk = Integer.parseInt(partes[i+1]);
                for (int j = (i+2); j < (i+2)+cantSk; j++) {
                    String nombreS = partes[j];
                    try {
                        sys.asociarClientePersonajeSkin(nombreC, nombreS);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                i+=(cantSk+1);
            }
        }
    }

    private static void menu(SistemaLigoOfLeyen sys) {
        Scanner scan = new Scanner(System.in);
        boolean salir = false;
        while(!salir){
            System.out.println("---------Inicio de sesión---------");
            System.out.println("Ingrese nombre de usuario: ");
            String nombre = scan.nextLine();
            System.out.println("Ingrese contraseña: ");
            String pass = scan.nextLine();
            try {
                int login = login(sys,nombre,pass);
                if (login==1){
                    salir = menuCliente(sys,nombre);
                }
                else if (login==2){
                    salir = menuAdmin(sys);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("¿Desea registrarse? (Y/N)");
                String registro = scan.nextLine();
                if (registro.equalsIgnoreCase("Y")){
                    registro(sys,nombre);
                }
                else{
                    System.out.println("¿Desea salir del sistema? (Y/N)");
                    String salir1 = scan.nextLine();
                    if (salir1.equalsIgnoreCase("Y")){
                        salir = true;
                    }
                }
            }
        }
    }

    private static int login(SistemaLigoOfLeyen sys, String nombre, String pass) {
        return sys.iniciarSesion(nombre, pass);
    }

    private static void registro(SistemaLigoOfLeyen sys, String bombre) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Registro de usuario");
        System.out.println("Nombre ingresado: "+bombre);
        System.out.println("Ingrese contraseña:");
        String contra = scan.nextLine();
        System.out.println("Ingrese nick:");
        String nick = scan.nextLine();
        System.out.println("Ingrese servidor:");
        String servidor = scan.nextLine();
        System.out.println("Su cuenta será iniciada con 0 RP");
        if (sys.añadirCliente(bombre, contra, nick, 0, 0, 0, servidor)){
            System.out.println("Usuario "+bombre+" registrado!");
        }
        else{
            System.out.println("Error en el registro.");
        }
    }

    private static boolean menuAdmin(SistemaLigoOfLeyen sys) {
        System.out.println("Menú Admin");
        Scanner scan = new Scanner(System.in);
        desplegarMenuAdmin();
        System.out.println("Ingrese número de opción: ");
        boolean repetir = true;
        int opcion = 0;
        while(repetir){
            try {
                opcion = Integer.parseInt(scan.nextLine());
                if (opcion>=-1 && opcion <=8){
                    repetir = false;
                }
                else{
                    throw new NullPointerException();
                }
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Error. Ingrese número de opción: ");
            }
        }
        while (true){
            try {
                if (opcion==1){
                    System.out.println("Recaudación por Rol:");
                    System.out.println(sys.mostrarRecaudacionPorRol());
                }
                if (opcion==2){
                    System.out.println("Recaudación por Servidor: ");
                    System.out.println(sys.mostrarRecaudacionTotal());
                }
                if (opcion==3){
                    System.out.println("Recaudación por personaje: ");
                    System.out.println(sys.mostrarRecaudacionPorPersonaje());
                }
                if (opcion==4){
                    System.out.println("Personajes por Rol:");
                    System.out.println(sys.mostrarCantidadPersonajePorRol());
                }
                if (opcion==5){
                    System.out.println("Ingrese nombre del nuevo personaje:");
                    String nombre = scan.nextLine();
                    System.out.println("Ingrese rol del nuevo personaje:");
                    String rol = scan.nextLine();
                    System.out.println("Ingrese cantidad de skins que tendrá el personaje:");
                    int c = Integer.parseInt(scan.nextLine());
                    if (sys.añadirPersonaje(nombre, rol, c)){
                        System.out.println("Personaje creado");
                    }
                    else{
                        System.out.println("Error, intentelo nuevamente con otro nombre de personaje");
                    }
                }
                if (opcion==6){
                    System.out.println("Ingrese nombre del personaje del nuevo skin:");
                    String nombreP = scan.nextLine();
                    System.out.println("Ingrese nombre del nuevo skin:");
                    String nombre = scan.nextLine();
                    System.out.println("Ingrese calidad del nuevo skin:");
                    String rol = scan.nextLine();
                    sys.crearSkinParaPersonaje(nombreP, rol, nombre);
                    System.out.println("Skin "+nombre+" creado para el personaje "+nombreP+" correctamente.");
                }
                if (opcion==7){
                    System.out.println("Ingrese nombre del jugador a bloquear:");
                    String nombre = scan.nextLine();
                    if (sys.bloquearJugador(nombre)){
                        System.out.println("Jugador "+nombre+" bloqueado."); 
                    }
                    else{
                        System.out.println("El jugador ya está bloqueado");
                    }
                }
                if (opcion==8){
                    System.out.println("Cuentas ordenadas:");
                    System.out.println(sys.mostrarCuentasOrdenadas());
                }
                if (opcion==0){
                    return false;
                }
                if (opcion==-1){
                    salir(sys);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            repetir = true;
            desplegarMenuAdmin();
            System.out.println("Ingrese número de opción: ");
            while(repetir){
                try {
                    opcion = Integer.parseInt(scan.nextLine());
                    if (opcion>=-1 && opcion <=8){
                        repetir = false;
                    }
                    else{
                        throw new NullPointerException();
                    }
                } catch (Exception e) {
                    System.out.println("Error. Ingrese número de opción: ");
                }
            }
        }
        
    }

    private static void desplegarMenuAdmin() {
        System.out.println("----Menu Admin");
        System.out.println("[ 1] Recaudación de ventas por rol");
        System.out.println("[ 2] Recaudación total");
        System.out.println("[ 3] Recaudación de ventas por personajes");
        System.out.println("[ 4] Cantidad de personajes por cada rol");
        System.out.println("[ 5] Agregar personajes al juego.");
        System.out.println("[ 6] Agregar skin");
        System.out.println("[ 7] Bloquear a jugador");
        System.out.println("[ 8] Desplegar todas las cuentas (de mayor a menor por cuenta)");
        System.out.println("[ 0] Salir de la sesión");
        System.out.println("[-1] Salir del sistema");
    }

    private static boolean menuCliente(SistemaLigoOfLeyen sys, String nombre) {
        System.out.println("Menú Cliente");
        Scanner scan = new Scanner(System.in);
        desplegarMenuCliente();
        System.out.println("Ingrese número de opción: ");
        boolean repetir = true;
        int opcion = 0;
        while(repetir){
            try {
                opcion = Integer.parseInt(scan.nextLine());
                if (opcion>=-1 && opcion <=7){
                    repetir = false;
                }
                else{
                    throw new NullPointerException();
                }
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Error. Ingrese número de opción: ");
            }
        }
        while (true){
            try {
                if (opcion==1){
                    System.out.println("Ingrese el nombre del personaje: ");
                    String personaje = scan.nextLine();
                    System.out.println("Ingrese el nombre de la skin: ");
                    String skin = scan.nextLine();
                    try {
                        sys.comprarSkin(nombre, skin);
                        System.out.println("Skin "+skin+" comprada.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (opcion==2){
                    try {
                        System.out.println("Ingresa el nombre del personaje: ");
                        String nombreP = scan.nextLine();
                        sys.comprarPersonaje(nombre, nombreP);
                        System.out.println("Personaje "+nombreP+" comprado.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                }
                if (opcion==3){
                    System.out.println("Skin que no tienes:");
                    System.out.println(sys.mostrarSkinsDisponibles(nombre));
                }
                if (opcion==4){
                    System.out.println("Inventario:");
                    System.out.println(sys.mostrarInventario(nombre));
                }
                if (opcion==5){
                    System.out.println("Ingrese monto de RP a recargar: ");
                    int rp = Integer.parseInt(scan.nextLine());
                    sys.recargarRP(nombre, rp);
                    System.out.println("RP recargado");
                }
                if (opcion==6){
                    System.out.println("Perfil:");
                    System.out.println(sys.mostrarDatosCuenta(nombre));
                }
                if (opcion==7){
                    System.out.println("Ingrese su actual contraseña");
                    String contraAntigua = scan.nextLine();
                    if (sys.comprobarContraseña(nombre, contraAntigua)){
                        System.out.println("Ingrese contraseña nueva:");
                        String contranuevaA = scan.nextLine();
                        System.out.println("Ingrese contraseña nueva denuevo: ");
                        String contranueva = scan.nextLine();
                        if (contranuevaA.equals(contranueva)){
                            sys.cambiarContraseña(nombre, contranueva);
                            System.out.println("Contraseña cambiada");
                        }
                        else{
                            System.out.println("Las contraseñas no coinciden!");
                        }
                    }
                    else{
                        System.out.println("La contraseña es incorrecta!");
                    }
                    
                }
                if (opcion==0){
                    return false;
                }
                if (opcion==-1){
                    salir(sys);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            repetir = true;
            desplegarMenuCliente();
            System.out.println("Ingrese número de opción: ");
            while(repetir){
                try {
                    opcion = Integer.parseInt(scan.nextLine());
                    if (opcion>=-1 && opcion <=8){
                        repetir = false;
                    }
                    else{
                        throw new NullPointerException();
                    }
                } catch (Exception e) {
                    System.out.println("Error. Ingrese número de opción: ");
                }
            }
        }
    }

    private static void desplegarMenuCliente() {
        System.out.println("----Menu Cliente");
        System.out.println("[ 1] Comprar Skin");
        System.out.println("[ 2] Comprar personaje");
        System.out.println("[ 3] Skins disponibles");
        System.out.println("[ 4] Mostrar inventario");
        System.out.println("[ 5] Recargar RP");
        System.out.println("[ 6] Mostrar datos cuenta");
        System.out.println("[ 7] Cambiar contraseña");
        System.out.println("[ 0] Salir de la sesión");
        System.out.println("[-1] Salir del sistema");
    }

    private static void salir(SistemaLigoOfLeyen sys) throws IOException {
        String cuentas = sys.obtenerCuentas();
        String esta = sys.obtenerEstadisticas();
        String pers = sys.obtenerPersonajes();
        FileWriter fileCuentas = new FileWriter("Cuentas.txt");
        PrintWriter escrituraCuentas = new PrintWriter(fileCuentas);
        escrituraCuentas.println(cuentas);
        fileCuentas.close();
        escrituraCuentas.close();
        FileWriter fileEs = new FileWriter("Estadisticas.txt");
        PrintWriter escrituraEs = new PrintWriter(fileEs);
        escrituraEs.println(esta);
        fileEs.close();
        escrituraEs.close();
        FileWriter filePersonajes = new FileWriter("Personajes.txt");
        PrintWriter escrituraPer = new PrintWriter(filePersonajes);
        escrituraPer.println(pers);
        filePersonajes.close();
        escrituraPer.close();
        System.exit(0);
    }
    
}
