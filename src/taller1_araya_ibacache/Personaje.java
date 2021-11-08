/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1_araya_ibacache;

/**
 *
 * @author jhona
 */
class Personaje {
    
    private String nombre;
    private String rol;
    private int recaudacion;
    private Skins listaSkins;
    private int recaudacionRol;

    public int getRecaudacionRol() {
        return recaudacionRol;
    }

    public void setRecaudacionRol(int recaudacionRol) {
        this.recaudacionRol = recaudacionRol;
    }

    public Personaje(String nombre, String rol, int cantidadSkin) {
        this.nombre = nombre;
        this.rol = rol;
        listaSkins = new Skins(1000);
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public int getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(int r){
        recaudacion = r;
    }
    
    public Skins getListaSkins() {
        return listaSkins;
    }
    
    
}
