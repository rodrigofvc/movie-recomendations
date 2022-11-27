package com.proav.movie.recomendations.utilidades;

/**
 *
 * Clase para describir las expresiones para filtrar información.
 */
public class Expresion {
    
    private OperadorEnum operador;
    private String variable;
    private String valor;
    
    public Expresion(OperadorEnum operador, String variable, String valor) {
        this.operador = operador;
        this.variable = variable;
        this.valor = valor;
    }

    public OperadorEnum getOperador() {
        return operador;
    }

    public String getVariable() {
        return variable;
    }

    public String getValor() {
        return valor;
    }
}
