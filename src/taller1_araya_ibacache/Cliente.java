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
public class Cliente {
    private String nombre;
    private String contraseña;
    private String nick;
    private int nivel;
    private int rp;
    private String servidor;
    private int recaudacionServidor;
    private PersonajesPoseidos personajes;
    private boolean bloqueado;

    public int getRecaudacionServidor() {
        return recaudacionServidor;
    }

    public void setRecaudacionServidor(int recaudacionServidor) {
        this.recaudacionServidor = recaudacionServidor;
    }
    
    public Cliente(String nombre, String contraseña, String nick, int nivel, int rp, int cantidadPersonajes, String servidor){
        bloqueado = false;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.nick = nick;
        this.nivel = nivel;
        this.rp = rp;
        this.servidor = servidor;
        personajes = new PersonajesPoseidos(1000);
    }

    public String getNombre() {
        return nombre;
    }
    
    public boolean isBloqueado(){
        return bloqueado;
    }
    
    public void setBloqueado(boolean f){
        bloqueado = f;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public PersonajesPoseidos getPersonajes() {
        return personajes;
    }

    public void setPersonajes(PersonajesPoseidos personajes) {
        this.personajes = personajes;
    }
    
}
