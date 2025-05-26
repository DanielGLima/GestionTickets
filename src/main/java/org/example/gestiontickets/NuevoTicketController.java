package org.example.gestiontickets;

import gestionTickets.ConexionDB;
import gestionTickets.Departamento;
import gestionTickets.SesionActual;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NuevoTicketController {

    @FXML
    private TextField textTitulo;
    @FXML
    private TextField textDescripcion;
    @FXML
    private ComboBox<Departamento> boxDepartamento;
    @FXML
    private ComboBox<String> boxPrioridad;
    @FXML
    private Button botonCrearTicket;
    @FXML
    private Button botonVolver;

    @FXML
    public void initialize() {
        cargarDepartamentos();
        cargarPrioridades();

        botonCrearTicket.setOnAction(e -> crearTicket());
        botonVolver.setOnAction(e -> volverAGestionTickets());
    }

    private void cargarDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT departamento_id, nombre, descripcion FROM Departamento";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departamentos.add(new Departamento(
                        String.valueOf(rs.getInt("departamento_id")),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }

            ObservableList<Departamento> opciones = FXCollections.observableArrayList(departamentos);
            boxDepartamento.setItems(opciones);

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar los departamentos", Alert.AlertType.ERROR);
        }
    }

    private void cargarPrioridades() {
        boxPrioridad.setItems(FXCollections.observableArrayList("Alta", "Media", "Baja"));
    }

    private void crearTicket() {
        String titulo = textTitulo.getText();
        String descripcion = textDescripcion.getText();
        Departamento departamento = boxDepartamento.getValue();
        String prioridad = boxPrioridad.getValue();

        if (titulo.isBlank() || titulo.length() < 3 || titulo.length() > 100) {
            mostrarAlerta("Error en el título", "El título debe tener entre 3 y 100 caracteres.", Alert.AlertType.WARNING);
            return;
        }

        if (descripcion.isBlank() || descripcion.length() < 5) {
            mostrarAlerta("Error en la descripción", "La descripción debe tener al menos 5 caracteres.", Alert.AlertType.WARNING);
            return;
        }

        if (departamento == null || prioridad == null) {
            mostrarAlerta("Campos incompletos", "Seleccione un departamento y una prioridad.", Alert.AlertType.WARNING);
            return;
        }


        String sql = "INSERT INTO Ticket (titulo, descripcion, estado_id, usuario_id, tecnico_id, departamento_id, prioridad) " +
                "VALUES (?, ?, ?, ?, NULL, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, descripcion);
            stmt.setInt(3, 1); // Estado inicial (por ejemplo: 'Pendiente')
            stmt.setInt(4, SesionActual.getUsuarioId());
            stmt.setInt(5, Integer.parseInt(departamento.getIdDepartamento()));
            stmt.setString(6, prioridad);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                mostrarAlerta("Éxito", "El ticket fue creado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            }

        } catch (SQLException e) {
            mostrarAlerta("Error de Base de Datos", "No se pudo guardar el ticket.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void volverAGestionTickets() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionTickets.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) botonVolver.getScene().getWindow();
            stage.setScene(new Scene(root, 853, 483));
            stage.setTitle("Gestión de Tickets");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver a la pantalla anterior", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        textTitulo.clear();
        textDescripcion.clear();
        boxDepartamento.setValue(null);
        boxPrioridad.setValue(null);
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
