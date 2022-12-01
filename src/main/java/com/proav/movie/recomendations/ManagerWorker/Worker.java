package com.proav.movie.recomendations.ManagerWorker;

import com.proav.movie.recomendations.utilidades.Expresion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * Clase para el comportamiento de cada Worker.
 */
public class Worker extends Thread {
    
    public final static String DIR_RESULTADO = "data/resultados.csv"; 
    private String archivo;
    private ArrayList<ArrayList<Expresion>> expresiones;
    private ArrayList<String> columnas;
     
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
        
        ArrayList<String> registrosBuscados = new ArrayList<>();
        File subarchivo = new File (this.archivo);
        try( FileReader fr = new FileReader(subarchivo);
            BufferedReader br = new BufferedReader(fr);) {
            String registro = br.readLine();
            // En la primera columna estan las columnas
            String[] columnaSubarchivo = registro.split(",");                                
            while (registro != null) {
                // Revisa cada registro
                registro = br.readLine();
                for (ArrayList<Expresion> listaExpresiones : expresiones) {
                    // Filtra las columnas 
                    if (satisfaceCondiciones(columnaSubarchivo, registro, listaExpresiones)) {
                        registrosBuscados.add(registro);
                        //escribeArchivo(registro, DIR_RESULTADO);
                    }                
                }                
            }
            // escribeRegistros(registrosBuscados);        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }
    /**
     * Revisa si el registro dado satisface las condiciones.
     * @param columnaSubarchivo 
     * @param registro 
     * @param listaExpresiones 
     */
    private boolean satisfaceCondiciones(String[] columnaSubarchivo, String registro, ArrayList<Expresion> listaExpresiones) {
        
        
        
        return true;
    }
 
    
    
}


