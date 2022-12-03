
package com.proav.movie.recomendations;

import com.proav.movie.recomendations.ManagerWorker.Manager;
import com.proav.movie.recomendations.utilidades.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sistema de recomendacion de peliculas
 */
public class Main {

    public static void main(String[] args) {
        int numHilos = getNumHilos();
        // Probando con un archivo de pocos registros
        String direccion = "data/out-users-8000_v2.csv";
        Divisor.divideArchivos(numHilos, direccion);
        Scanner sc = new Scanner(System.in);
        System.out.println("Indique las columnas a seleccionar, separadas únicamente por comas.");
        String select = sc.nextLine();
        System.out.println("Escriba las condiciones de filtrado en forma normal disyuntiva.");
        String expr = sc.nextLine();
        sc.close();
        // Interprete envia las clausulas de filtrado....
        ArrayList<ArrayList<Expresion>> expresiones = Parser.analiza(expr);
        String[] select2 = select.split(",");
        // Realiza el filtrado sobre los workers
        Manager.filtraInformacion(numHilos, expresiones, select2);
        
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
        ComparadorEnum op = ComparadorEnum.MAYOR_IGUAL; 
        Expresion expresion = new Expresion(op, "year", "2010");
        ArrayList<Expresion> filtrado = new ArrayList<Expresion>();
        filtrado.add(expresion);
        expresiones.add(filtrado);        
        return expresiones;
    }
}
