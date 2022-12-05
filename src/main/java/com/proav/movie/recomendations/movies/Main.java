
package com.proav.movie.recomendations.movies;

import com.proav.movie.recomendations.ManagerWorker.Manager;
import com.proav.movie.recomendations.utilidades.Divisor;
import com.proav.movie.recomendations.utilidades.Expresion;
import com.proav.movie.recomendations.utilidades.Parser;


import estadisticas.GraficaBarras;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Sistema de recomendacion de peliculas
 */
public class Main extends Application{

    int anchoVentana;
    int altoVentana;
    Stage escenario;
    Label labelSelect;
    Label labelWhere;
    Button ejecuta;
    TextField inputSelect;
    TextField inputWhere;
    VBox caja;
    Button avanzaGrafica;
    Button retrocedeGrafica;

    @Override
    public void start(Stage primaryStage) {
        anchoVentana = 500;
        altoVentana = 500;
        escenario = primaryStage;

        caja = new VBox();

        labelSelect = new Label(" Indique las columnas a seleccionar ");

        inputSelect = new TextField();
        inputSelect.setPrefWidth(anchoVentana-100);

        labelWhere = new Label(" Escriba las condiciones de filtrado en forma normal disyuntiva. ");

        inputWhere = new TextField();
        inputWhere.setPrefWidth(anchoVentana-100);

        GraficaBarras grafica = new GraficaBarras();

        ejecuta = new Button(" Ejecutar consulta ");
        ejecuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ejecutaConsulta();
                grafica.reset();
            }
        });


        avanzaGrafica = new Button("Avanzar");
        avanzaGrafica.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               grafica.avanza();
           }
        });

        retrocedeGrafica = new Button("Retroceder");
        retrocedeGrafica.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            grafica.retrocede();
          }
        });

        HBox cajaBotones = new HBox();
        cajaBotones.setSpacing(20);
        cajaBotones.getChildren().addAll(ejecuta, retrocedeGrafica, avanzaGrafica);
        caja.getChildren().addAll(labelSelect, inputSelect, labelWhere, inputWhere, cajaBotones);
        caja.setSpacing(10);

        VBox cajaGraph = new VBox(grafica.getBarChart());
        cajaGraph.setMaxWidth(1500);
        cajaGraph.setAlignment(Pos.TOP_CENTER);
        caja.getChildren().add(cajaGraph);

        Scene scene = new Scene(caja, anchoVentana, altoVentana);
        escenario.setTitle("Consulta");
        escenario.setScene(scene);
        escenario.show();
    }

    /**
     * @return El número de hilos a usar es 4 veces el número de CPU's según la JVM.
     */
    public int getNumHilos() {
        int CPUs = Runtime.getRuntime().availableProcessors();
        return CPUs*4;
    }

    public void ejecutaConsulta(){
        int numHilos = getNumHilos();
        // Probando con un archivo de pocos registros
        String direccion = "data/out-users-8000_v2.csv";
        Divisor.divideArchivos(numHilos, direccion);
        String select = inputSelect.getText();
        String expr = inputWhere.getText();
        // Interprete envia las clausulas de filtrado....
        ArrayList<ArrayList<Expresion>> expresiones = Parser.analiza(expr);
        if (!select.contains("title")) {
          select += ", title";
        }
        if (!select.contains("rating")) {
          select += ", rating";
        }
        // Realiza el filtrado sobre los workers
        Manager.filtraInformacion(numHilos, expresiones, select);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Query");
        alert.setHeaderText("Consulta finalizada.");
        alert.setContentText("Revisar archivo data/resultado.csv");
        alert.showAndWait();

        inputSelect.setText("");
        inputWhere.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
