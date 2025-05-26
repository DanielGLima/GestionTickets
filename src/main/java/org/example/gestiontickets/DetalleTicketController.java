package org.example.gestiontickets;

import gestionTickets.CRUD.EstadoTicketDAO;
import gestionTickets.ConexionDB;
import gestionTickets.EstadoTicket;
import gestionTickets.SesionActual;
import gestionTickets.Tecnico;
import gestionTickets.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DetalleTicketController {

    @FXML private Label labelTitulo;
    @FXML private Label labelPrioridad;
    @FXML private Label labelCreacion;
    @FXML private Label labelDescripcion;

    @FXML private ComboBox<EstadoTicket> boxEstado;
    @FXML private TextArea textAreaNota;

    @FXML private Button botonAccion;
    @FXML private Button botonVolver;

    private Ticket ticket;
    private boolean esPropietario;

    @FXML
    public void initialize() {
        // Cargar estados desde la base de datos
        boxEstado.getItems().addAll(EstadoTicketDAO.obtenerTodosLosEstados());

        // Mostrar solo el nombre en la UI
        boxEstado.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(EstadoTicket item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        boxEstado.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(EstadoTicket item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        botonAccion.setOnAction(e -> manejarAccion());
        botonVolver.setOnAction(e -> volver());
    }

    public void inicializarDatos(Ticket ticketSeleccionado) {
        this.ticket = ticketSeleccionado;

        labelTitulo.setText(ticket.getTitulo());
        labelPrioridad.setText(ticket.getPrioridad());
        labelCreacion.setText(ticket.getFechaCreacion().toString());
        labelDescripcion.setText(ticket.getDescripcion());

        // Seleccionar estado actual
        for (EstadoTicket estado : boxEstado.getItems()) {
            if (estado.getNombre().equals(ticket.getEstadoActual().getNombre())) {
                boxEstado.setValue(estado);
                break;
            }
        }

        Tecnico tecnicoAsignado = ticket.getTecnicoAsignado();
        int idTecnicoActual = SesionActual.getUsuarioId();
        esPropietario = tecnicoAsignado != null && tecnicoAsignado.getIdPersona() == idTecnicoActual;

        if (tecnicoAsignado == null) {
            botonAccion.setText("Tomar Ticket");
            boxEstado.setDisable(true);
            textAreaNota.setDisable(true);
        } else if (esPropietario) {
            botonAccion.setText("Actualizar");
            boxEstado.setDisable(false);
            textAreaNota.setDisable(false);
        } else {
            botonAccion.setText("No disponible");
            botonAccion.setDisable(true);
            boxEstado.setDisable(true);
            textAreaNota.setDisable(true);
        }
    }

    private void manejarAccion() {
        if (ticket.getTecnicoAsignado() == null) {
            tomarTicket();
        } else if (esPropietario) {
            actualizarTicket();
        }
    }

    private void tomarTicket() {
        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE ticket SET tecnico_asignado_id = ?, estado = ? WHERE ticket_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, SesionActual.getUsuarioId());
            stmt.setString(2, "En proceso");
            stmt.setInt(3, ticket.getIdTicket());
            stmt.executeUpdate();

            registrarNota(conn, "El técnico tomó el ticket.");

            boxEstado.setDisable(false);
            textAreaNota.setDisable(false);
            botonAccion.setText("Actualizar");

            Tecnico tecnico = new Tecnico(SesionActual.getUsuarioId(), SesionActual.getNombreUsuario(), "", "", null);
            ticket.setTecnicoAsignado(tecnico);
            EstadoTicket estado = EstadoTicketDAO.obtenerEstadoPorNombre("En proceso");
            if (estado != null) {
                ticket.setEstadoActual(estado);
                boxEstado.setValue(estado);
            }

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo tomar el ticket.");
            e.printStackTrace();
        }
    }

    private void actualizarTicket() {
        EstadoTicket nuevoEstado = boxEstado.getValue();
        String nota = textAreaNota.getText();

        if (nuevoEstado == null) {
            mostrarAlerta("Validación", "Debes seleccionar un estado.");
            return;
        }

        if (nota == null || nota.isBlank()) {
            mostrarAlerta("Validación", "La nota no puede estar vacía.");
            return;
        }

        try (Connection conn = ConexionDB.conectar()) {
            String sql = "UPDATE ticket SET estado = ? WHERE ticket_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nuevoEstado.getNombre());
            stmt.setInt(2, ticket.getIdTicket());
            stmt.executeUpdate();

            registrarNota(conn, nota);
            mostrarAlerta("Éxito", "Ticket actualizado.");
            textAreaNota.clear();
            ticket.setEstadoActual(nuevoEstado);

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo actualizar el ticket.");
            e.printStackTrace();
        }
    }

    private void registrarNota(Connection conn, String textoNota) throws SQLException {
        String sql = "INSERT INTO nota (ticket_id, usuario_id, contenido, fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, ticket.getIdTicket());
        stmt.setInt(2, SesionActual.getUsuarioId());
        stmt.setString(3, textoNota);
        stmt.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
        stmt.executeUpdate();
    }

    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gestiontickets/TicketsPendientes.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) botonVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo volver a la vista anterior.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
