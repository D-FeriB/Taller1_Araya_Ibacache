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
public class PersonajesPoseidos {
    
    private int cantidad;
    private int max;
    private PersonajePoseido[] lista;
    
    public PersonajesPoseidos(int max) {
        this.max = max;
        cantidad = 0;
        lista = new PersonajePoseido[max];
    }
    public int getCantidad(){
        return cantidad;
    }
    
    public int getMax(){
        return max;
    }
    
    public boolean añadirPersonajePoseido(PersonajePoseido p){
        if (cantidad<max){
            lista[cantidad] = p;
            cantidad++;
            return true;
        }
        else{
            return false;
        }
    }
    
    public PersonajePoseido obtenerPersonajeI(int index){
        return index<cantidad? lista[index] : null;
    }
    
    public PersonajePoseido buscarPersonaje(String nombre){
        for (int i = 0; i < cantidad ; i++){
            if (lista[i].getPersonaje().getNombre().equals(nombre)){
                return lista[i];
            }
        }
        return null;
    }
    
}
