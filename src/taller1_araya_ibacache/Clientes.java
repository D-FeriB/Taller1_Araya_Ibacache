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
public class Clientes {
    
    private int cantidad;
    private int max;
    private Cliente[] lista;
    
    public Clientes(int cantidad){
        this.cantidad = 0;
        this.max = cantidad;
        lista = new Cliente[cantidad];
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public int getMax(){
        return max;
    }
    
    public boolean añadirCliente(Cliente cliente){
        if (cantidad<max){
            lista[cantidad] = cliente;
            cantidad++;
            return true;
        }
        else{
            return false;
        }
    }
    
    public Cliente obtenerClienteI(int index){
        return index<cantidad? lista[index] : null;
    }
    
    public Cliente buscarCliente(String rut){
        for (int i = 0; i < cantidad ; i++){
            if (lista[i].getNombre().equals(rut)){
                return lista[i];
            }
        }
        return null;
    }
    
    public void ordenar(){
        for (int i = 0; i <= cantidad-2; i++) {
            for (int j = i+1; j <= cantidad-1; j++) {
                if (lista[i].getNivel()>lista[j].getNivel()){
                    Cliente aux = lista[i];
                    lista[i] = lista[j];
                    lista[j] = aux;
                }
            }
        }
    }
}
