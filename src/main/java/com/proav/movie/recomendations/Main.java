package com.proav.movie.recomendations;

import com.proav.movie.recomendations.ManagerWorker.Manager;
import com.proav.movie.recomendations.utilidades.Divisor;
import com.proav.movie.recomendations.utilidades.Expresion;
import com.proav.movie.recomendations.utilidades.OperadorEnum;
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
        // Realiza el filtrado sobre los workers
        Manager.filtraInformacion(numHilos, expresiones);
        
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
