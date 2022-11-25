package com.proav.movie.recomendations.ManagerWorker;

import com.proav.movie.recomendations.utilidades.Expresion;
import java.util.ArrayList;

/**
 *
 * Clase para el comportamiento de cada Worker.
 */
public class Worker extends Thread {
    
    private String archivo;
    private ArrayList<ArrayList<Expresion>> expresiones;
    
    /**
     * Recibe el archivo sobre el cual va a trabajar.
     * @param archivo el archivo a realizar la operación de filtrado.
     */
    public Worker(String archivo, ArrayList<ArrayList<Expresion>> expresiones) {
        this.archivo = archivo;
        this.expresiones = expresiones;
    }
    
    
    @Override
    public void run() {
       manejaArchivo(); 
    }
    
    /**
     * Hace el filtrado de información del subarchivo que le tocó.
     */
    private void manejaArchivo() {
        System.out.println(">>>>> Worker " + this.getName() + " trabajando con " + this.archivo);
        Manager.agregaRegistro(this.getName());
    }
    
}
