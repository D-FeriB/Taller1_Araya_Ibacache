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
public class Personajes {
    
    private int cantidad;
    private int max;
    private Personaje[] lista;
    
    public Personajes(int cantidad){
        this.cantidad = 0;
        this.max = cantidad;
        lista = new Personaje[cantidad];
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public int getMax(){
        return max;
    }
    
    public boolean añadirPersonaje(Personaje p){
        if (cantidad<max){
            lista[cantidad] = p;
            cantidad++;
            return true;
        }
        else{
            return false;
        }
    }
    
    public Personaje obtenerPersonajeI(int index){
        return index<cantidad? lista[index] : null;
    }
    
    public Personaje buscarPersonaje(String nombre){
        for (int i = 0; i < cantidad ; i++){
            if (lista[i].getNombre().equals(nombre)){
                return lista[i];
            }
        }
        return null;
    }
    
}
