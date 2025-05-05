package org.example.gestiontickets;

import gestionTickets.CRUD.UsuarioCRUD;
import gestionTickets.Departamento;
import gestionTickets.Rol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class CreacionDeUsuariosDeAdministradorController {

    @FXML private TextField labelNombre;
    @FXML private TextField labelCorreo;
    @FXML private TextField labelNombreUsuario;
    @FXML private PasswordField contrasenia;
    @FXML private ComboBox<Rol> boxRol;
    @FXML private ComboBox<Departamento> boxDepartamento;
    @FXML private Button botonGuardar;

    private final UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
    private final ObservableList<Rol> roles = FXCollections.observableArrayList();
    private final ObservableList<Departamento> departamentos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Cargar roles y departamentos desde BD
        roles.setAll(usuarioCRUD.obtenerRoles());
        departamentos.setAll(usuarioCRUD.obtenerDepartamentos());

        boxRol.setItems(roles);
        boxDepartamento.setItems(departamentos);

        botonGuardar.setOnAction(e -> guardarUsuario());
    }

    private void guardarUsuario() {
        String nombre = labelNombre.getText();
        String correo = labelCorreo.getText();
        String usuario = labelNombreUsuario.getText();
        String clave = contrasenia.getText();
        Rol rol = boxRol.getValue();
        Departamento depto = boxDepartamento.getValue();

        if (nombre.isBlank() || correo.isBlank() || usuario.isBlank() || clave.isBlank() || rol == null || depto == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Faltan datos", "Por favor, completa todos los campos.");
            return;
        }

        boolean exito = usuarioCRUD.insertarUsuario(nombre, correo, usuario, clave, rol.getIdRol(), depto.getIdDepartamento());

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
        labelNombre.clear();
        labelCorreo.clear();
        labelNombreUsuario.clear();
        contrasenia.clear();
        boxRol.getSelectionModel().clearSelection();
        boxDepartamento.getSelectionModel().clearSelection();
    }
}
