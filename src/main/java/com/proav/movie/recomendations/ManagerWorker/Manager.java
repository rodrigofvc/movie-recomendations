package com.proav.movie.recomendations.ManagerWorker;

import com.proav.movie.recomendations.utilidades.Expresion;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Clase para modelar el Manager.
 */
public class Manager {
    
    /**
     * Registros que recibe de los workers.
     */
    public static ArrayList<String> registros = new ArrayList<String>();
    
    /**
     * Crea un pool de hilos y obten la información de cada hilo.
     * @param numHilos hilos a crear en el pool.
     * @param expresiones filtros para los registros.
     */
    public static void filtraInformacion(int numHilos, ArrayList<ArrayList<Expresion>> expresiones) {
        
        ExecutorService poolWorkers = Executors.newFixedThreadPool(numHilos);
        
        for (int i = 0; i < numHilos; i++) {
            String subarchivo = "subarchivo-" + Integer.toString(i+1) + ".csv";
            poolWorkers.execute(new Worker(subarchivo, expresiones));
        }
        
        int cantidadWorkers = Thread.activeCount() - 1; 
        System.out.println("Threads actuales: " + cantidadWorkers);
        
        poolWorkers.shutdown();
        while (! poolWorkers.isTerminated()) {            
        }
        
        System.out.println("REGISTROS RECIBIDOS DE LOS WORKERS");
        for (String r : registros) {
            System.out.println(r);
        }
        
    }
    
    /**
     * Guarda los registros que los workers envian.
     * @param registro el registro que el worker envia.
     */
    public synchronized static void agregaRegistro(String registro) {
        registros.add(registro);
    }
    
}
