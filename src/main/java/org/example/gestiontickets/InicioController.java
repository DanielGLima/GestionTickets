package org.example.gestiontickets;

import gestionTickets.ConexionDB;
import gestionTickets.SesionActual;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InicioController {

    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Button entrarButton;

    @FXML
    public void initialize() {
        entrarButton.setOnAction(event -> {
            String usuario = usuarioField.getText();
            String contrasena = contrasenaField.getText();

            if (usuario.isBlank() || contrasena.isBlank()) {
                mostrarAlerta("Campos vacíos", "Por favor ingresa el usuario y la contraseña.");
                return;
            }

            try (Connection conn = ConexionDB.conectar()) {
                String sql = "SELECT usuario_id, nombre_completo, rol_id FROM Usuario WHERE nombre_usuario = ? AND contrasena = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, contrasena);
                ResultSet rs = stmt.executeQuery();


                if (rs.next()) {
                    int usuarioId = rs.getInt("usuario_id");
                    String nombreCompleto = rs.getString("nombre_completo");
                    int rolId = rs.getInt("rol_id");

                    String rolTexto = switch (rolId) {
                        case 1 -> "Administrador";
                        case 2 -> "Tecnico";
                        case 3 -> "Usuario";
                        default -> "Desconocido";
                    };
                    SesionActual.iniciarSesion(usuarioId, nombreCompleto, rolTexto);

                    switch (rolId) {
                        case 1: // Administrador
                            abrirPantalla("AdministradorDashboard.fxml", "Administrador");
                            break;
                        case 2: // Técnico
                            abrirPantalla("TecnicosDashboard.fxml", "Tecnicos.");
                            break;
                        case 3: // Usuario común
                            abrirPantalla("UsuarioDashboard.fxml", "Usuarios");
                            break;
                        default:
                            mostrarAlerta("Rol no reconocido", "El rol del usuario no es válido.");
                            break;
                    }

                } else {
                    mostrarAlerta("Error de autenticación", "Usuario o contraseña incorrectos.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
            }
        });
    }

    private void abrirPantalla(String fxml, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        Stage stage = (Stage) entrarButton.getScene().getWindow();
        Scene scene = new Scene(root, 853, 483);
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
