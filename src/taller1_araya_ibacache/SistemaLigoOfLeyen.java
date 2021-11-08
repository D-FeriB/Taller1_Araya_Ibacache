/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1_araya_ibacache;

/**
 *
 * @author araya_ibacache
 */
public interface SistemaLigoOfLeyen {
    public int iniciarSesion(String nombre, String contraseña);
    public boolean añadirPersonaje(String nombre, String rol, int cantidadSkins);
    public void añadirRecaudacionAPersonaje(String nombre, int recaudacion);
    public boolean añadirSkin(String nombre, String calidad);
    public void asociarRecaudacionPersonaje(String nombre, int c);
    public void asociarPersonajeSkin(String nombre, String nombreSkin);
    public void crearSkinParaPersonaje(String nombreP,String calidad, String nombreS);
    public boolean añadirCliente(String nombre, String contraseña, String nick, int nivel, int rp, int cantidadPersonajes, String servidor);
    public void asociarClientePersonaje(String nombrePersonaje, String nombreCliente);
    public void asociarClientePersonajeSkin(String nombreCliente, String nombreSkin);
    public boolean comprarPersonaje(String nombreCliente, String nombrePersonaje);
    public boolean comprarSkin(String nombreCliente, String nombreSkin);
    public String mostrarInventario(String nombreCliente);
    public String mostrarSkinsDisponibles(String nombreCliente);
    public void recargarRP(String nombreCliente, int rp);
    public boolean comprobarContraseña(String nombreCliente, String contraseña);
    public boolean cambiarContraseña(String nombreCliente, String nuevaContraseña);
    public String mostrarRecaudacionPorRol();
    public String mostrarRecaudacionTotal();
    public String mostrarRecaudacionPorPersonaje();
    public String  mostrarCantidadPersonajePorRol();
    public String mostrarCuentasOrdenadas();
    public boolean bloquearJugador(String nombreJugador);
    public String obtenerEstadisticas();
    public String obtenerCuentas();
    public String obtenerPersonajes();

    public String mostrarDatosCuenta(String nombre);
}
