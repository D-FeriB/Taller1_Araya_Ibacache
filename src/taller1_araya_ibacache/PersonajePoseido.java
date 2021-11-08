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
public class PersonajePoseido {
    private Cliente dueño;
    private Personaje personaje;
    private Skins skinsPoseidas;
    
    public PersonajePoseido(){
        dueño = null;
        personaje = null;
        skinsPoseidas = new Skins(500);
    }

    public Cliente getDueño() {
        return dueño;
    }

    public void setDueño(Cliente dueño) {
        this.dueño = dueño;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public Skins getSkinsPoseidas() {
        return skinsPoseidas;
    }

    public void setSkinsPoseidas(Skins skinsPoseidas) {
        this.skinsPoseidas = skinsPoseidas;
    }
    
    
}
