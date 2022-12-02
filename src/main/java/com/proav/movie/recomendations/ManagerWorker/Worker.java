package com.proav.movie.recomendations.ManagerWorker;

import com.proav.movie.recomendations.utilidades.Expresion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Clase para el comportamiento de cada Worker.
 */
public class Worker extends Thread {
    
    // Direccion del archivo donde se guardan los resultados de cada worker
    public final static String DIR_RESULTADO = "data/resultados.csv"; 
    // El buffer general para escribir
    public static BufferedWriter bufferResultado;
    private String archivo;
    private ArrayList<ArrayList<Expresion>> expresiones;
     
    /**
     * Recibe el archivo sobre el cual va a trabajar.
     * @param archivo el archivo a realizar la operación de filtrado.
     * @param expresiones las listas de expresiones para filtrar el archivo.
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
                    if (satisfaceCondiciones(columnaSubarchivo, registro, listaExpresiones) && registro != null) {
                        registrosBuscados.add(registro);
                        escribeArchivo(registro+ "\n");
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
     * Escribe de manera concurrente un registro en un archivo especifico para guardar los resultados.
     * @param registro el registro a guardar en el archivo general.
     */
    private synchronized void escribeArchivo(String registro) {
        try {
            bufferResultado.write(registro);
            bufferResultado.flush();
        } catch (IOException e) {
            System.out.printf("%s no pudo escribir en el archivo %s%n", this.getName(), DIR_RESULTADO);
        } catch (Exception e) {
            System.out.printf("Ocurrio un error de escritura con el %s", this.getName());
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


