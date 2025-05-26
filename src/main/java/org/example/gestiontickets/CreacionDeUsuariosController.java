package org.example.gestiontickets;

import gestionTickets.CRUD.UsuarioCRUD;
import gestionTickets.ConexionDB;
import gestionTickets.UsuarioTabla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreacionDeUsuariosController {

    @FXML private TextField nombreCompletoField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private TextField departamentoField;
    @FXML private PasswordField contrasenaField;
    @FXML private Button registrarButton;
    @FXML private Button botonRegresar;
    @FXML private TableView<UsuarioTabla> tablaUsuarios;
    @FXML private TableColumn<UsuarioTabla, String> ColNombre;
    @FXML private TableColumn<UsuarioTabla, String> colCorreo;
    @FXML private TableColumn<UsuarioTabla, String> conTelefono;
    @FXML private TableColumn<UsuarioTabla, String> colDepartamento;
    @FXML private TableColumn<UsuarioTabla, String> colContrasenia;
    @FXML private Button botonActualizar;
    @FXML private Button botonEliminar;


    @FXML
    public void initialize() {
        botonRegresar.setOnAction(event -> volverAlInicio());
        registrarButton.setOnAction(event -> registrarUsuario());
        botonActualizar.setOnAction(event -> actualizarUsuario());
        botonEliminar.setOnAction(event -> eliminarUsuario());

        configurarTabla();
        cargarUsuarios();

        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nombreCompletoField.setText(newSel.getNombre());
                correoField.setText(newSel.getCorreo());
                telefonoField.setText(newSel.getTelefono());
                contrasenaField.setText(newSel.getContrasena());
                departamentoField.setText(newSel.getDepartamento());
            }
        });
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

    private void configurarTabla() {
        ColNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colCorreo.setCellValueFactory(data -> data.getValue().correoProperty());
        conTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());
        colDepartamento.setCellValueFactory(data -> data.getValue().departamentoProperty());
        colContrasenia.setCellValueFactory(data -> data.getValue().contrasenaProperty());
    }


    private void registrarUsuario() {
        String nombre = nombreCompletoField.getText();
        String correo = correoField.getText();
        String telefono = telefonoField.getText();
        String contrasena = contrasenaField.getText();
        String nombreDepartamento = departamentoField.getText();

        if (nombre.isBlank() || correo.isBlank() || telefono.isBlank() || contrasena.isBlank() || nombreDepartamento.isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos.");
            return;
        }

        try (Connection conn = ConexionDB.conectar()) {
            // Obtener departamento_id a partir del nombre
            PreparedStatement stmtDep = conn.prepareStatement("SELECT departamento_id FROM departamento WHERE nombre = ?");
            stmtDep.setString(1, nombreDepartamento);
            ResultSet rsDep = stmtDep.executeQuery();

            if (!rsDep.next()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Departamento no encontrado", "No existe un departamento con ese nombre.");
                return;
            }

            int departamentoId = rsDep.getInt("departamento_id");

            // Insertar usuario
            PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO usuario (nombre_completo, correo_electronico, nombre_usuario, telefono, contrasena, rol_id, departamento_id)
            VALUES (?, ?, ?, ?, ?, 3, ?)
        """);

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, correo); // usando correo como nombre_usuario
            stmt.setString(4, telefono);
            stmt.setString(5, contrasena);
            stmt.setInt(6, departamentoId);

            stmt.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado correctamente.");
            limpiarCampos();
            cargarUsuarios();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario.");
        }
    }


    private void actualizarUsuario() {
        UsuarioTabla usuario = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un usuario", "Debes seleccionar un usuario de la tabla.");
            return;
        }

        String nuevoNombre = nombreCompletoField.getText();
        String nuevoCorreo = correoField.getText();
        String nuevoTelefono = telefonoField.getText();
        String nuevaContrasena = contrasenaField.getText();
        String nombreDepartamento = departamentoField.getText();

        if (nuevoNombre.isBlank() || nuevoCorreo.isBlank() || nuevoTelefono.isBlank() || nuevaContrasena.isBlank() || nombreDepartamento.isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos.");
            return;
        }

        try (Connection conn = ConexionDB.conectar()) {
            // Verificamos si el departamento existe
            PreparedStatement checkDep = conn.prepareStatement("SELECT departamento_id FROM departamento WHERE nombre = ?");
            checkDep.setString(1, nombreDepartamento);
            ResultSet rsDep = checkDep.executeQuery();

            if (!rsDep.next()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Departamento no encontrado", "No existe un departamento con ese nombre.");
                return;
            }

            int departamentoId = rsDep.getInt("departamento_id");

            // Procedemos a actualizar
            PreparedStatement stmt = conn.prepareStatement("""
            UPDATE usuario 
            SET nombre_completo = ?, correo_electronico = ?, telefono = ?, contrasena = ?, departamento_id = ?
            WHERE usuario_id = ?
        """);

            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevoCorreo);
            stmt.setString(3, nuevoTelefono);
            stmt.setString(4, nuevaContrasena);
            stmt.setInt(5, departamentoId);
            stmt.setInt(6, usuario.getId());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Actualizado", "Usuario actualizado correctamente.");
                cargarUsuarios();
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin cambios", "No se realizó ningún cambio.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el usuario.");
        }
    }


    private void eliminarUsuario() {
        UsuarioTabla usuario = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un usuario", "Debes seleccionar un usuario de la tabla.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminar usuario");
        confirm.setHeaderText("¿Estás seguro de eliminar a " + usuario.getNombre() + "?");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try (Connection conn = ConexionDB.conectar()) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuario WHERE usuario_id = ?");
                    stmt.setInt(1, usuario.getId());
                    stmt.executeUpdate();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "Usuario eliminado.");
                    cargarUsuarios();
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario.");
                }
            }
        });
    }





    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarUsuarios() {
        ObservableList<UsuarioTabla> lista = FXCollections.observableArrayList();

        String sql = """
        SELECT u.usuario_id, u.nombre_completo, u.correo_electronico, u.telefono, 
               COALESCE(d.nombre, 'Sin departamento') AS departamento, 
               u.contrasena
        FROM usuario u
        LEFT JOIN departamento d ON u.departamento_id = d.departamento_id
        WHERE u.rol_id = 3
    """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new UsuarioTabla(
                        rs.getInt("usuario_id"),
                        rs.getString("nombre_completo"),
                        rs.getString("correo_electronico"),
                        rs.getString("telefono"),
                        rs.getString("departamento"),
                        rs.getString("contrasena")
                ));
            }

            tablaUsuarios.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los usuarios.");
        }
    }


    private void limpiarCampos() {
        nombreCompletoField.clear();
        correoField.clear();
        telefonoField.clear();
        contrasenaField.clear();
        departamentoField.clear();
    }
}
