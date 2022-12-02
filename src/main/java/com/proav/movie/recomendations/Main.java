package com.proav.movie.recomendations;

import com.proav.movie.recomendations.ManagerWorker.Manager;
import com.proav.movie.recomendations.ManagerWorker.Worker;
import com.proav.movie.recomendations.utilidades.Divisor;
import com.proav.movie.recomendations.utilidades.Expresion;
import com.proav.movie.recomendations.utilidades.OperadorEnum;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Sistema de recomendacion de peliculas
 */
public class Main {

    public static void main(String[] args) {
        int numHilos = getNumHilos();
        // Probando con un archivo de pocos registros
        String direccion = "data/out-users-8000_v2.csv";
        Divisor.divideArchivos(numHilos, direccion);
        // Interprete envia las clausulas de filtrado....
        ArrayList<ArrayList<Expresion>> expresiones = getExpresionesFiltrado();
        inicializaArchivoResultados();    
        // Realiza el filtrado sobre los workers
        Manager.filtraInformacion(numHilos, expresiones);
        
    }
    
    public static void inicializaArchivoResultados() {
        try {            
            File file = new File(Worker.DIR_RESULTADO);
            file.createNewFile();
            FileWriter fw = new FileWriter(Worker.DIR_RESULTADO);
            Worker.bufferResultado = new BufferedWriter(fw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return El número de hilos a usar es 4 veces el número de CPU's según la JVM.
     */
    public static int getNumHilos() {
        int CPUs = Runtime.getRuntime().availableProcessors();
        return CPUs*4;
    }


    /**
     * Funcion temporal para construir expresiones a evaluar.
     */
    public static ArrayList<ArrayList<Expresion>> getExpresionesFiltrado() {
        ArrayList<ArrayList<Expresion>> expresiones = new ArrayList<ArrayList<Expresion>>();
        OperadorEnum op = OperadorEnum.IGUALDAD; 
        Expresion expresion = new Expresion(op, "title", "hello world");
        ArrayList<Expresion> filtrado = new ArrayList<Expresion>();
        filtrado.add(expresion);
        expresiones.add(filtrado);        
        return expresiones;
    }
}
