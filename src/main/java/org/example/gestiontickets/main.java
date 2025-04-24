package org.example.gestiontickets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 853, 483);
        stage.setTitle("Sistema de Tickets");
        stage.setScene(scene);
        stage.show();
    }

    
//    public void start(Stage stage) throws IOException {
//        // Cambiamos "Inicio.fxml" por "AdministradorDashboard.fxml"
//        // para cargar el dashboard del administrador directamente
//        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("AdministradorDashboard.fxml"));

//        Scene scene = new Scene(fxmlLoader.load(), 853, 483);
//        stage.setTitle("Sistema de Tickets");
//        stage.setScene(scene);
//        stage.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}