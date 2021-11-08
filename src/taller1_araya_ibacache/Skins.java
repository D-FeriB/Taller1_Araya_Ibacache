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
class Skins {
    
    private int cantidad;
    private int max;
    private Skin[] lista;
    
    public Skins(int cantidad){
        this.cantidad = 0;
        this.max = cantidad;
        lista = new Skin[cantidad];
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public int getMax(){
        return max;
    }
    
    public boolean añadirSkin(Skin skin){
        if (cantidad<max){
            lista[cantidad] = skin;
            cantidad++;
            return true;
        }
        else{
            return false;
        }
    }
    
    public Skin obtenerSkinI(int index){
        return index<cantidad? lista[index] : null;
    }
    
    public Skin buscarSkin(String nombre){
        for (int i = 0; i < cantidad ; i++){
            if (lista[i].getNombre().equals(nombre)){
                return lista[i];
            }
        }
        return null;
    }
}
