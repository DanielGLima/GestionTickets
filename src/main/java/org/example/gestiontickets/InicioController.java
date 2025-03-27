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

public class InicioController {
    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Button entrarButton;
    @FXML
    private Button crearUsuarioButton;

    @FXML
    public void initialize() {
        crearUsuarioButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CreacionDeUsuarios.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) crearUsuarioButton.getScene().getWindow();
                Scene scene = new Scene(root, 853, 483);
                stage.setScene(scene);
                stage.setTitle("Registro de Usuario");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}