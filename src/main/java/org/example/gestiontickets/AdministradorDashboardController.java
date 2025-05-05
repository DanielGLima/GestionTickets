package org.example.gestiontickets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministradorDashboardController {

    @FXML private Button botonGestionUsuarios;
    @FXML private Button botonGestionDepartamentos;
    @FXML private Button botonConfiguracion;
    @FXML private Button botonCerrarSesion;
    @FXML private Button botonGestionTecnicos;
    @FXML private Button botonGestionTickets;
    @FXML private Button botonFlujos;

    @FXML
    public void initialize() {
        botonGestionUsuarios.setOnAction(e -> abrirVentana("CreacionDeUsuarios.fxml", "Gestión de Usuarios"));
        botonGestionDepartamentos.setOnAction(e -> abrirVentana("GestionDepartamentos.fxml", "Gestión de Departamentos"));
        botonGestionTecnicos.setOnAction(e -> abrirVentana("GestionTecnicos.fxml", "Gestión de Técnicos"));
        botonGestionTickets.setOnAction(e -> abrirVentana("GestionTickets.fxml", "Gestión de Tickets"));
        botonFlujos.setOnAction(e -> abrirVentana("GestionFlujos.fxml", "Flujos y Estados"));
        botonConfiguracion.setOnAction(e -> abrirVentana("ConfiguracionSistema.fxml", "Configuración del Sistema"));

        botonCerrarSesion.setOnAction(e -> abrirVentana("Inicio.fxml", "Inicio de sesión"));
    }

    private void abrirVentana(String nombreFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreFXML));
            Parent root = loader.load();
            Stage stage = (Stage) botonCerrarSesion.getScene().getWindow(); // Puedes usar cualquier botón
            stage.setScene(new Scene(root, 853, 483));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
