/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.proav.movie.recomendations;

import com.proav.movie.recomendations.utilidades.Divisor;
import java.util.concurrent.Executor;

/**
 * Sistema de recomendacion de peliculas
 */
public class Main {

    public static void main(String[] args) {
        int numHilos = getNumHilos();
        System.out.println(numHilos);
        // Probando con un archivo de pocos registros
        String direccion = "data/out-users-8000.csv";
        Divisor.divideArchivos(numHilos, direccion);
    
    
    
    }
    
    /**
     * El número de hilos a usar es 4 veces el número de CPU's según la JVM.
     */
    public static int getNumHilos() {
        int CPUs = Runtime.getRuntime().availableProcessors();
        return CPUs*4;
    }
}
