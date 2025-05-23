package org.example.gestiontickets;

import gestionTickets.CRUD.UsuarioCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreacionDeUsuariosController {

    @FXML private TextField nombreCompletoField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private PasswordField contrasenaField;
    @FXML private PasswordField confirmarContrasenaField;
    @FXML private Button registrarButton;
    @FXML private Button botonRegresar;

    @FXML
    public void initialize() {
        botonRegresar.setOnAction(event -> volverAlInicio());
        registrarButton.setOnAction(event -> registrarUsuario());
    }

    private void volverAlInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministradorDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) botonRegresar.getScene().getWindow();
            stage.setScene(new Scene(root, 853, 483));
            stage.setTitle("Inicio de Sesión");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de inicio.");
            e.printStackTrace();
        }
    }

    private void registrarUsuario() {
        String nombre = nombreCompletoField.getText();
        String correo = correoField.getText();
        String telefono = telefonoField.getText();
        String contrasena = contrasenaField.getText();
        String confirmar = confirmarContrasenaField.getText();

        if (nombre.isBlank() || correo.isBlank() || telefono.isBlank() || contrasena.isBlank() || confirmar.isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos.");
            return;
        }

        if (!contrasena.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña", "Las contraseñas no coinciden.");
            return;
        }

        UsuarioCRUD crud = new UsuarioCRUD();
        boolean exito = crud.insertarUsuario(
                nombre,
                correo,
                correo,    // usando correo como nombre_usuario por ahora
                contrasena,
                "3",       // rol_id como texto (usuario común)
                "1"        // departamento_id por defecto (puedes cambiarlo)
        );

        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        nombreCompletoField.clear();
        correoField.clear();
        telefonoField.clear();
        contrasenaField.clear();
        confirmarContrasenaField.clear();
    }
}
