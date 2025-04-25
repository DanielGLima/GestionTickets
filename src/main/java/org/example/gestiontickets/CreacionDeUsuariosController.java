package org.example.gestiontickets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreacionDeUsuariosController {
    @FXML
    private TextField nombreCompletoField;
    @FXML
    private TextField correoField;
    @FXML
    private TextField telefonoField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private PasswordField confirmarContrasenaField;
    @FXML
    private Button registrarButton;
    @FXML
    private Button volverButton;

    @FXML
    public void initialize() {
        volverButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) volverButton.getScene().getWindow();
                Scene scene = new Scene(root, 853, 483);
                stage.setScene(scene);
                stage.setTitle("Inicio de Sesión");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        registrarButton.setOnAction(event -> {
            String nombre = nombreCompletoField.getText();
            String correo = correoField.getText();
            String telefono = telefonoField.getText();
            String contrasena = contrasenaField.getText();
            String confirmar = confirmarContrasenaField.getText();

            if (!contrasena.equals(confirmar)) {
                System.out.println("⚠️ Las contraseñas no coinciden.");
                return;
            }

            gestionTickets.CRUD.UsuarioCRUD crud = new gestionTickets.CRUD.UsuarioCRUD();
            crud.crearUsuario(
                    nombre,
                    correo,
                    correo,     // usamos el correo como nombre_usuario por ahora
                    contrasena,
                    telefono,
                    3           // ID del rol (ajustar según tu base de datos)
            );
        });

    }
}