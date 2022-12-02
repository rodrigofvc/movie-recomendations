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
    
    private static final String DIR_SUBARCHIVOS = "data/output/subarchivo-";
    
    /**
     * Crea un pool de hilos y obten la información de cada hilo.
     * @param numHilos hilos a crear en el pool.
     * @param expresiones filtros para los registros.
     */
    public static void filtraInformacion(int numHilos, ArrayList<ArrayList<Expresion>> expresiones) {
        
        ExecutorService poolWorkers = Executors.newFixedThreadPool(numHilos);
        
        for (int i = 0; i < numHilos; i++) {
            String subarchivo = DIR_SUBARCHIVOS + Integer.toString(i+1) + ".csv";
            poolWorkers.execute(new Worker(subarchivo, expresiones));
        }
        
        int cantidadWorkers = Thread.activeCount() - 1; 
        System.out.println("Threads actuales: " + cantidadWorkers);
        
        poolWorkers.shutdown();
        while (! poolWorkers.isTerminated()) {            
        }
        // Cierra el buffer cuando todos los Threads terminaron su tarea
        cierraBufferConcurrente();        
    }
    
    /**
     * Una vez que los hilos hayan terminado su tarea, cierra el buffer.
     */
    private static void cierraBufferConcurrente() {
        try {
            Worker.bufferResultado.close();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
