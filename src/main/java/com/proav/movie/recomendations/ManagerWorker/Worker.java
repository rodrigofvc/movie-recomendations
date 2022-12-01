package com.proav.movie.recomendations.ManagerWorker;

import com.proav.movie.recomendations.utilidades.Expresion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

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
            // En la primera fila estan las columnas
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
     * Le asigna un número dependiendo del tipo de columna
     * @param nombreColumna
     * @return 0 si es genres, 1 si es tipo double, 2 si es entero, 3 en otro caso.
     */
    private int tipoColumna(String nombreColumna){
        switch(nombreColumna){
            case "genres": return 0;
            case "rating": return 1;
            case "idRating":
            case "userId":
            case "movieId":
            case "timestamp":
            case "year":
            case "age": return 2;
            default: return 3;
        }
    }
    
    /**
     * Revisa si el registro dado satisface las condiciones.
     * @param columnaSubarchivo 
     * @param registro 
     * @param listaExpresiones 
     */
    private boolean satisfaceCondiciones(String[] columnaSubarchivo, String registro, ArrayList<Expresion> listaExpresiones) {
        HashMap<String, Integer> tablaNombres = new HashMap<>();
        for(int i=0; i<columnaSubarchivo.length; i++){
            tablaNombres.put(columnaSubarchivo[i], i);
        }
        String[] registroSeparado = registro.split(",");
        // Como es una lista de conjunciones, se devuelve falso al primero que
        // no cumpla con la condición.
        for(Expresion expr : listaExpresiones){
            String valorEsperado = expr.getValor();
            int indCol = tablaNombres.get(expr.getVariable());
            String valorReal = registroSeparado[indCol];
            Comparable vrComp;
            Comparable veComp;
            switch(tipoColumna(expr.getVariable())){
                case 1 : vrComp = Double.valueOf(valorReal);
                         veComp = Double.valueOf(valorEsperado);
                break;
                case 2 : vrComp = Integer.valueOf(valorReal);
                         veComp = Integer.valueOf(valorEsperado);
                break;
                default: vrComp = valorReal;
                         veComp = valorEsperado;
            }
            
            if(tipoColumna(expr.getVariable()) == 0){
                String[] generos = valorReal.split("\\|");
                switch(expr.getComparador()){
                    case IGUALDAD:
                    if(!valorReal.contains(valorEsperado)){
                        return false;
                    }
                    break;
                    case DIFERENTE:
                    if(valorReal.contains(valorEsperado)){
                        return false;
                    }
                    break;
                    case MAYOR_IGUAL:
                    for(String gen : generos){
                        if(gen.compareTo(valorEsperado) < 0){
                            return false;
                        }
                    }
                    break;
                    case MENOR_IGUAL:
                    for(String gen : generos){
                        if(gen.compareTo(valorEsperado) > 0){
                            return false;
                        }
                    }
                    break;
                    case MAYOR:
                    for(String gen : generos){
                        if(gen.compareTo(valorEsperado) < 0
                         ||gen.equals(valorEsperado)){
                            return false;
                        }
                    }
                    break;
                    case MENOR:
                    for(String gen : generos){
                        if(gen.compareTo(valorEsperado) > 0
                         ||gen.equals(valorEsperado)){
                            return false;
                        }
                    }
                }
            }else{
                switch(expr.getComparador()){
                    case IGUALDAD:
                    if(!vrComp.equals(veComp)){
                        return false;
                    }
                    break;
                    case DIFERENTE:
                    if(vrComp.equals(veComp)){
                        return false;
                    }
                    break;
                    case MAYOR_IGUAL:
                    if(vrComp.compareTo(veComp) < 0){
                        return false;
                    }
                    break;
                    case MENOR_IGUAL:
                    if(vrComp.compareTo(veComp) > 0){
                        return false;
                    }
                    break;
                    case MAYOR:
                    if(vrComp.compareTo(veComp) <= 0){
                        return false;
                    }
                    break;
                    case MENOR:
                    if(vrComp.compareTo(veComp) >= 0){
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }
 
    
    
}


