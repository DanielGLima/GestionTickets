package org.example.gestiontickets;

import gestionTickets.SesionActual;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TecnicoDashboradController {

    @FXML private Label labelTecnico;
    @FXML private Button BotonTicketsPendientes;
    @FXML private Button BotonHistorial;
    @FXML private Button botonCerrarSesion;

    @FXML
    public void initialize() {
        // Mostrar nombre del técnico
        labelTecnico.setText("Bienvenido, " + SesionActual.getNombreUsuario());

        // Abrir Tickets Pendientes
        BotonTicketsPendientes.setOnAction(e -> abrirVista("/org/example/gestiontickets/TicketsPendientes.fxml"));

        // Abrir Historial de Tickets
        BotonHistorial.setOnAction(e -> abrirVista("/org/example/gestiontickets/HistorialTickets.fxml"));

        // Cerrar sesión y volver al login
        botonCerrarSesion.setOnAction(e -> {
            SesionActual.cerrarSesion();
            abrirVista("/org/example/gestiontickets/inicio.fxml");
        });
    }

    private void abrirVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = (Stage) labelTecnico.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + rutaFXML);
        }
    }

}
