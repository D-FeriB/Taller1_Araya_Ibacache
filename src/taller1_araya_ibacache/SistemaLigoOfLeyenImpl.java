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
public class SistemaLigoOfLeyenImpl implements SistemaLigoOfLeyen{
    
    private Skins skins;
    private Personajes personajes;
    private Clientes clientes;
    private int cantidadTop;
    private int cantidadMid;
    private int cantidadAdc;
    private int cantidadJg;
    private int cantidadSup;
    
    public SistemaLigoOfLeyenImpl(){
        skins = new Skins(1000);
        personajes = new Personajes(1000);
        clientes = new Clientes(1000);
    }
    
    @Override
    public int iniciarSesion(String nombre, String contraseña) {
        if (nombre.equalsIgnoreCase("ADMIN")){
            if (contraseña.equalsIgnoreCase("ADMIN")){
                return 2;
            }
        }
        Cliente cliente = clientes.buscarCliente(nombre);
        if (cliente!=null){
            if (cliente.getContraseña().equals(contraseña)){
                return 1;
            }
            else{
                throw new NullPointerException("Contraseña incorrecta!");
            }
        }
        else{
            throw new NullPointerException("Usuario incorrecto!");
        }
    }

    @Override
    public boolean añadirPersonaje(String nombre, String rol, int cantidadSkins) {
        Personaje personajeExistente = personajes.buscarPersonaje(nombre);
        if (personajeExistente == null){
            /**
             * no existe
             */
            if (rol.equalsIgnoreCase("TOP")){
                cantidadTop++;
            }
            if (rol.equalsIgnoreCase("MID")){
                cantidadMid++;
            }
            if (rol.equalsIgnoreCase("SUP")){
                cantidadSup++;
            }
            if (rol.equalsIgnoreCase("JG")){
                cantidadJg++;
            }
            if (rol.equalsIgnoreCase("ADC")){
                cantidadAdc++;
            }
            return personajes.añadirPersonaje(new Personaje(nombre,rol,cantidadSkins));
        }
        else{
            return false;
        }
    }

    @Override
    public boolean añadirSkin(String nombre, String calidad) {
        Skin skinExistente = skins.buscarSkin(nombre);
        if (skinExistente==null){
            return skins.añadirSkin(new Skin(nombre, calidad));
        }
        else{
            /**
             * ya existe
             */
            return false;
        }
    }

    @Override
    public boolean añadirCliente(String nombre, String contraseña, String nick, int nivel, int rp, int cantidadPersonajes, String servidor) {
        return clientes.añadirCliente(new Cliente(nombre,contraseña,nick,nivel,rp,cantidadPersonajes,servidor));
    }

    @Override
    public void asociarClientePersonaje(String nombrePersonaje, String nombreCliente) {
        Personaje pBuscado = personajes.buscarPersonaje(nombrePersonaje);
        Cliente cBuscado = clientes.buscarCliente(nombreCliente);
        if (pBuscado!=null && cBuscado!=null){
            PersonajePoseido personajePoseido = new PersonajePoseido();
            if (cBuscado.getPersonajes().buscarPersonaje(nombrePersonaje)==null){
                if (cBuscado.getPersonajes().añadirPersonajePoseido(personajePoseido)){
                    personajePoseido.setDueño(cBuscado);
                    personajePoseido.setPersonaje(pBuscado);
                }
                else{
                    throw new NullPointerException("El jugador llegó a su máxima cantidad de personajes");
                }
            }
            else{
                throw new NullPointerException("El jugador ya posee el personaje");
            }
        }
        else{
            throw new NullPointerException("El jugadoor o el cliente no existen");
        }
    }

    @Override
    public void asociarClientePersonajeSkin(String nombreCliente, String nombreSkin) {
        Skin s = skins.buscarSkin(nombreSkin);
        Cliente c = clientes.buscarCliente(nombreCliente);
        if (s!=null && c!=null){
            String pName = s.getPersonaje().getNombre();
            PersonajePoseido p = c.getPersonajes().buscarPersonaje(pName);
            if (p!=null){
                p.getSkinsPoseidas().añadirSkin(s);
            }
            else{
                throw new NullPointerException("El cliente no tiene al personaje "+pName+" por lo tanto no puede obtener sus skins.");
            }
        }
        else{
            throw new NullPointerException("El skin no existe");
        }
    }

    @Override
    public boolean comprarPersonaje(String nombreCliente, String nombrePersonaje) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        Personaje p = personajes.buscarPersonaje(nombrePersonaje);
        if (c!=null && p!=null){
            if (c.getPersonajes().buscarPersonaje(nombrePersonaje)==null){
                if (c.getRp()>=975){
                    PersonajePoseido pp = new PersonajePoseido();
                    pp.setDueño(c);
                    pp.setPersonaje(p);
                    if (c.getPersonajes().añadirPersonajePoseido(pp)){
                        c.setRp(c.getRp()-975);
                        pp.getPersonaje().setRecaudacion(pp.getPersonaje().getRecaudacion()+975);
                        c.setRecaudacionServidor(c.getRecaudacionServidor()+975);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    throw new NullPointerException("El usuario no tiene suficiente dinero");
                }
            }
            else{
                throw new NullPointerException("El usuario ya tiene al personaje");
            }
        }
        else{
            throw new NullPointerException("El personaje no existe en el juego");
        }
    }

    @Override
    public boolean comprarSkin(String nombreCliente, String nombreSkin) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        Skin s = skins.buscarSkin(nombreSkin);
        if (c!=null && s!=null){
            String nP = s.getPersonaje().getNombre();
            if (c.getPersonajes().buscarPersonaje(nP)==null){
                throw new NullPointerException("El usuario no posee al personaje "+nP+" por lo que no puede comprar sus skins.");
            }
            else{
                PersonajePoseido p = c.getPersonajes().buscarPersonaje(nP);
                int precio = s.getPrecio();
                if (precio<=c.getRp()){
                    c.setRp(c.getRp()-precio);
                    p.getPersonaje().setRecaudacion(p.getPersonaje().getRecaudacion()+precio);
                    c.setRecaudacionServidor(c.getRecaudacionServidor()+precio);
                    return p.getSkinsPoseidas().añadirSkin(s);
                }
                else{
                    throw new NullPointerException("El usuario no posee el dinero suficiente. Saldo disponible: "+c.getRp()+" RP");
                }
            }
        }
        else{
            throw new NullPointerException("El skin no existe");
        }
    }

    @Override
    public String mostrarInventario(String nombreCliente) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        if (c!=null){
            String text = "";
            for (int i = 0; i < c.getPersonajes().getCantidad(); i++) {
                PersonajePoseido p = c.getPersonajes().obtenerPersonajeI(i);
                text+=p.getPersonaje().getNombre()+":\n";
                for (int j = 0; j < p.getSkinsPoseidas().getCantidad(); j++) {
                    Skin s = p.getSkinsPoseidas().obtenerSkinI(j);
                    text+="\t"+s.getNombre()+"\n";
                }
            }
            return text;
        }
        else{
            throw new NullPointerException("El cliente no existe");
        }
    }

    @Override
    public String mostrarSkinsDisponibles(String nombreCliente) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        if (c!=null){
            String text = "";
            for (int i = 0; i < skins.getCantidad(); i++) {
                Skin consultada = skins.obtenerSkinI(i);
                boolean match = false;
                for (int j = 0; j < c.getPersonajes().getCantidad(); j++) {
                    if (match){
                        break;
                    }
                    PersonajePoseido p = c.getPersonajes().obtenerPersonajeI(j);
                    for (int k = 0; k < p.getSkinsPoseidas().getCantidad(); k++) {
                        Skin s = p.getSkinsPoseidas().obtenerSkinI(k);
                        if (s.getNombre().equals(consultada.getNombre())){
                            match = true;
                            break;
                        }
                    }
                }
                if (!match){
                    text+=consultada.getNombre()+"\n";
                }
            }
            if (text.equals("")){
                return "Posee todas las skins disponibles.";
            }
            return text;
        }
        else{
            throw new NullPointerException("El cliente no existe.");
        }
    }

    @Override
    public void recargarRP(String nombreCliente, int rp) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        if (c==null){
            throw new NullPointerException("No existe el cliente");
        }
        else{
            c.setRp(c.getRp()+rp);
        }
    }

    @Override
    public boolean comprobarContraseña(String nombreCliente, String contraseña) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        if (c!=null){
            return c.getContraseña().equals(contraseña);
        }
        else{
            throw new NullPointerException("El cliente no existe");
        }
    }

    @Override
    public boolean cambiarContraseña(String nombreCliente, String nuevaContraseña) {
        Cliente c = clientes.buscarCliente(nombreCliente);
        if (c!=null){
            c.setContraseña(nuevaContraseña);
            return true;
        }
        else{
            throw new NullPointerException("El cliente no existe");
        }
    }

    @Override
    public String mostrarRecaudacionPorRol() {
        String text = "Recaudación por Rol\n";
        int clpADC = 0;
        int clpMID = 0;
        int clpSUP = 0;
        int clpTOP = 0;
        int clpJG = 0;
        for (int i = 0; i < personajes.getCantidad(); i++) {
            Personaje p = personajes.obtenerPersonajeI(i);
            if (p.getRol().equalsIgnoreCase("SUP")){
                clpSUP+=p.getRecaudacion();
            }
            if (p.getRol().equalsIgnoreCase("MID")){
                clpMID+=p.getRecaudacion();
            }
            if (p.getRol().equalsIgnoreCase("ADC")){
                clpADC+=p.getRecaudacion();
            }
            if (p.getRol().equalsIgnoreCase("TOP")){
                clpTOP+=p.getRecaudacion();
            }
            if (p.getRol().equalsIgnoreCase("JG")){
                clpJG+=p.getRecaudacion();
            }
        }
        clpADC = (int)(clpADC*6.15);
        text+="\tADC: "+clpADC+" CLP\n";
        clpMID = (int)(clpMID*6.15);
        text+="\tMID: "+clpMID+" CLP\n";
        clpSUP = (int)(clpSUP*6.15);
        text+="\tSUP: "+clpSUP+" CLP\n";
        clpTOP = (int)(clpTOP*6.15);
        text+="\tTOP: "+clpTOP+" CLP\n";
        clpJG  = (int)(clpJG *6.15);
        text+="\tJG: "+clpJG+" CLP\n";
        return text;
    }

    @Override
    public String mostrarRecaudacionTotal() {
        String[] servers = {"LAS","LAN","EUW","KR","NA","RU"};
        String text = "";
        for (String server : servers) {
            int total = 0;
            for (int i = 0; i < clientes.getCantidad(); i++) {
                Cliente c = clientes.obtenerClienteI(i);
                if (c.getServidor().equals(server)){
                    total+=c.getRecaudacionServidor(); 
                }
            }
            total = (int)(total*6.15);
            text+= "La recaudación total de "+server+"es : "+total+" CLP\n";
        }
        return text;
    }

    @Override
    public String mostrarRecaudacionPorPersonaje() {
        String text = "Recaudación por personaje\n";
        for (int i = 0; i < personajes.getCantidad(); i++) {
            Personaje p= personajes.obtenerPersonajeI(i);
            int rec = ((int)(p.getRecaudacion()*6.15));
            text+="\t"+p.getNombre()+":"+rec+"CLP\n";
        }
        return text;
    }

    @Override
    public String mostrarCantidadPersonajePorRol() {
        String text = "Cantidad de Personaje Por Rol\n";
        text+="\tSup: "+cantidadSup+"\n";
        text+="\tMid: "+cantidadMid+"\n";
        text+="\tADC: "+cantidadAdc+"\n";
        text+="\tTop: "+cantidadTop+"\n";
        text+="\tJg: "+cantidadJg+"\n";
        return text;
    }

    @Override
    public String mostrarCuentasOrdenadas() {
        String text = "Cuentas por nivel:\n";
        clientes.ordenar();
        for (int i = 0; i < clientes.getCantidad(); i++) {
            text+="\t"+clientes.obtenerClienteI(i).getNick()+" "+clientes.obtenerClienteI(i).getNivel()+"\n";
        }
        return text;
    }

    @Override
    public boolean bloquearJugador(String nombreJugador) {
        Cliente c = clientes.buscarCliente(nombreJugador);
        if (c==null){
            throw new NullPointerException("El jugador no existe!");
        }
        if (c.isBloqueado()){
            return false;
        }
        else{
            c.setBloqueado(true);
            return true;
        }
    }

    @Override
    public String obtenerEstadisticas() {
        String text ="";
        for (int i = 0; i < personajes.getCantidad(); i++) {
            if (i==personajes.getCantidad()-1){
                text+=personajes.obtenerPersonajeI(i).getNombre()+","+personajes.obtenerPersonajeI(i).getRecaudacion();
            }
            else{
                text+=personajes.obtenerPersonajeI(i).getNombre()+","+personajes.obtenerPersonajeI(i).getRecaudacion()+"\n";
            }
        }
        return text;
    }

    @Override
    public String obtenerCuentas() {
        String text = "";
        for (int i = 0; i < clientes.getCantidad(); i++) {
            Cliente c = clientes.obtenerClienteI(i);
            text+=c.getNombre()+","+c.getContraseña()+","+c.getNick()+","+c.getNivel()+","+c.getRp()+","+c.getPersonajes().getCantidad();
            for (int j = 0; j < c.getPersonajes().getCantidad(); j++) {
                PersonajePoseido p = c.getPersonajes().obtenerPersonajeI(j);
                text+=","+p.getPersonaje().getNombre()+","+p.getSkinsPoseidas().getCantidad();
                for (int k = 0; k < p.getSkinsPoseidas().getCantidad(); k++) {
                    Skin s = p.getSkinsPoseidas().obtenerSkinI(k);
                    text+=","+s.getNombre();
                }
            }
            text+=","+c.getServidor();
            if (i!=clientes.getCantidad()-1){
                text+="\n";
            }
            
        }
        return text;
    }

    @Override
    public String obtenerPersonajes() {
        String text = "";
        for (int i = 0; i < personajes.getCantidad(); i++) {
            Personaje p = personajes.obtenerPersonajeI(i);
            text+=p.getNombre()+","+p.getRol()+","+p.getListaSkins().getCantidad();
            for (int j = 0; j < p.getListaSkins().getCantidad(); j++) {
                Skin s = p.getListaSkins().obtenerSkinI(j);
                text+=","+s.getNombre()+","+s.getCalidad();
            }
            if (i!=personajes.getCantidad()-1){
                text+="\n";
            }
        }
        return text;
    }

    @Override
    public void añadirRecaudacionAPersonaje(String nombre, int recaudacion) {
        Personaje p = personajes.buscarPersonaje(nombre);
        if (p==null){
            throw new NullPointerException("El personaje no existe");
            
        }
        else{
            p.setRecaudacion(p.getRecaudacion()+recaudacion);
        }
    }

    @Override
    public void asociarPersonajeSkin(String nombre, String nombreSkin) {
        Personaje p = personajes.buscarPersonaje(nombre);
        Skin s = skins.buscarSkin(nombreSkin);
        if (p==null || s==null){
            throw new NullPointerException("El skin o el personaje no existen");
        }
        else{
            s.setPersonaje(p);
            p.getListaSkins().añadirSkin(s);
        }
    }

    @Override
    public void crearSkinParaPersonaje(String nombreP, String calidad, String nombreS) {
        if (!calidad.equalsIgnoreCase("M") && !calidad.equalsIgnoreCase("D") && !calidad.equalsIgnoreCase("L") && !calidad.equalsIgnoreCase("E") && !calidad.equalsIgnoreCase("N")){
            throw new NullPointerException("La calidad descrita no existe");   
        }
       Personaje p = personajes.buscarPersonaje(nombreP);
       if (p==null){
           throw new NullPointerException("El personaje no existe");
       }
       else{
           if (skins.buscarSkin(nombreS)==null){
                if (!p.getListaSkins().añadirSkin(new Skin(nombreS, calidad))){
                    throw new NullPointerException("El personaje tiene demasiadas skins");
                }    
           }
           else{
               throw new NullPointerException("El skin ya existe en el sistema");
           }
       }
    }

    @Override
    public void asociarRecaudacionPersonaje(String nombre, int c) {
        Personaje p = personajes.buscarPersonaje(nombre);
        if (p==null){
            throw new NullPointerException("El personaje no existe");
        }
        else{
            p.setRecaudacion(c);
        }
    }

    @Override
    public String mostrarDatosCuenta(String nombre) {
        Cliente c = clientes.buscarCliente(nombre);
        if (c==null){
            throw new NullPointerException("La cuenta no existe!");
        }
        else{
            String eext = "Nombre: "+c.getNombre()+",Rp: "+c.getRp()+", Nick: "+c.getNick()+", Contraseña: ***********"+c.getContraseña().substring(c.getContraseña().length()-3, c.getContraseña().length());
            return eext;
        }
    }
    
}
