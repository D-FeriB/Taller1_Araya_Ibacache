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
public class Skin {
    
    private String nombre;
    private String calidad;
    private int precio;
    private Personaje personaje;
    
    public Skin(String nombre, String calidad){
        this.nombre = nombre;
        this.calidad = calidad;
        if (calidad.equalsIgnoreCase("M")){
            precio = 3250;
        }
        if (calidad.equalsIgnoreCase("D")){
            precio = 2750;
        }
        if (calidad.equalsIgnoreCase("L")){
            precio = 1820;
        }
        if (calidad.equalsIgnoreCase("E")){
            precio = 1350;
        }
        if (calidad.equalsIgnoreCase("N")){
            precio = 975;
        }
        personaje = null;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalidad() {
        return calidad;
    }

    public int getPrecio() {
        return precio;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }
     
}