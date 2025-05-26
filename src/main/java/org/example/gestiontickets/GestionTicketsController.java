package org.example.gestiontickets;

import gestionTickets.ConexionDB;
import gestionTickets.TicketTabla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GestionTicketsController {

    @FXML private TableView<TicketTabla> tablaTickets;
    @FXML private TableColumn<TicketTabla, Integer> colId;
    @FXML private TableColumn<TicketTabla, String> colTitulo;
    @FXML private TableColumn<TicketTabla, String> colEstado;
    @FXML private TableColumn<TicketTabla, String> colPrioridad;
    @FXML private TableColumn<TicketTabla, String> colTecnico;
    @FXML private TableColumn<TicketTabla, String> colDepartamento;

    @FXML private TextField campoBuscar;
    @FXML private ComboBox<String> filtroEstado;
    @FXML private ComboBox<String> filtroPrioridad;
    @FXML private ComboBox<String> filtroTecnico;
    @FXML private TextField filtroDepartamento;

    @FXML private Button botonNuevoTicket;
    @FXML private Button botonVerDetalle;
    @FXML private Button botonAsignarTecnico;
    @FXML private Button botonCambiarEstado;
    @FXML private Button botonEliminarTicket;

    private ObservableList<TicketTabla> listaTickets = FXCollections.observableArrayList();
    private FilteredList<TicketTabla> filtro;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        colTecnico.setCellValueFactory(new PropertyValueFactory<>("tecnico"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        botonVerDetalle.setOnAction(e -> verDetalleTicket());
        botonAsignarTecnico.setOnAction(e -> asignarTecnico());
        botonCambiarEstado.setOnAction(e -> cambiarEstado());
        botonNuevoTicket.setOnAction(event -> abrirVistaNuevoTicket());
        botonEliminarTicket.setOnAction(e -> eliminarTicket());

        cargarTickets();
        cargarFiltros();

        campoBuscar.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroEstado.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroPrioridad.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroTecnico.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroDepartamento.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());


    }

    private void cargarTickets() {
        listaTickets.clear();
        String sql = """
                SELECT t.ticket_id, t.titulo, t.descripcion, e.nombre AS estado, t.prioridad,
               COALESCE(u.nombre_completo, 'Sin asignar') AS tecnico,
               d.nombre AS departamento
        FROM ticket t
        JOIN estadoticket e ON t.estado_id = e.estado_id
        JOIN departamento d ON t.departamento_id = d.departamento_id
        LEFT JOIN usuario u ON t.tecnico_id = u.usuario_id
        
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaTickets.add(new TicketTabla(
                        rs.getInt("ticket_id"),
                        rs.getString("titulo"),
                        rs.getString("estado"),
                        rs.getString("prioridad"),
                        rs.getString("tecnico"),
                        rs.getString("departamento"),
                        rs.getString("descripcion")
                ));
            }

            filtro = new FilteredList<>(listaTickets, p -> true);
            tablaTickets.setItems(filtro);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los tickets.", Alert.AlertType.ERROR);
        }
    }

    private String obtenerFechaHoraTicket(int id) {
        String sql = "SELECT fecha_creacion FROM ticket WHERE ticket_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("fecha_creacion").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No disponible";
    }


    private void cargarFiltros() {
        try (Connection conn = ConexionDB.conectar()) {
            filtroEstado.setItems(cargarCombo(conn, "SELECT nombre FROM estadoticket"));
            filtroPrioridad.setItems(FXCollections.observableArrayList("Alta", "Media", "Baja"));
            filtroTecnico.setItems(cargarCombo(conn, """
                     SELECT u.nombre_completo
                        FROM usuario u
                        JOIN rol r ON u.rol_id = r.rol_id
                        WHERE LOWER(r.nombre) = 'tecnico'
                    """));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void verDetalleTicket() {
        TicketTabla ticket = tablaTickets.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un ticket.", Alert.AlertType.WARNING);
            return;
        }

        String detalle = String.format(
                "ID: %d\nTítulo: %s\nEstado: %s\nPrioridad: %s\nTécnico: %s\nDepartamento: %s\nFecha creación: %s\n\nDescripción:\n%s",
                ticket.getId(),
                ticket.getTitulo(),
                ticket.getEstado(),
                ticket.getPrioridad(),
                ticket.getTecnico(),
                ticket.getDepartamento(),
                obtenerFechaHoraTicket(ticket.getId()),
                ticket.getDescripcion()
        );


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle del Ticket");
        alert.setHeaderText("Información completa del ticket");
        alert.setContentText(detalle);
        alert.showAndWait();
    }


    private ObservableList<String> cargarCombo(Connection conn, String sql) throws Exception {
        ObservableList<String> lista = FXCollections.observableArrayList();
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(rs.getString(1));
            }
        }
        return lista;
    }

    private void aplicarFiltros() {
        String texto = campoBuscar.getText().toLowerCase();
        String estado = filtroEstado.getValue();
        String prioridad = filtroPrioridad.getValue();
        String tecnico = filtroTecnico.getValue();
        String departamento = filtroDepartamento.getText().toLowerCase();

        filtro.setPredicate(ticket -> {
            boolean coincideBuscar = ticket.getTitulo().toLowerCase().contains(texto);
            boolean coincideEstado = estado == null || estado.equals(ticket.getEstado());
            boolean coincidePrioridad = prioridad == null || prioridad.equals(ticket.getPrioridad());
            boolean coincideTecnico = tecnico == null || tecnico.equals(ticket.getTecnico());
            boolean coincideDepartamento = ticket.getDepartamento().toLowerCase().contains(departamento);

            return coincideBuscar && coincideEstado && coincidePrioridad && coincideTecnico && coincideDepartamento;
        });
    }
    private void actualizarEstado(int ticketId, String estado) {
        String sql = """
        UPDATE ticket SET estado_id = (
            SELECT estado_id FROM estadoticket WHERE nombre = ?
        ) WHERE ticket_id = ?
    """;
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, ticketId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cambiarEstado() {
        TicketTabla ticket = tablaTickets.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un ticket.", Alert.AlertType.WARNING);
            return;
        }

        ObservableList<String> estados = FXCollections.observableArrayList();
        String sql = "SELECT nombre FROM estadoticket";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                estados.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, estados);
        dialog.setTitle("Cambiar Estado");
        dialog.setHeaderText("Selecciona un nuevo estado para el ticket");
        dialog.setContentText("Estado:");

        dialog.showAndWait().ifPresent(estado -> {
            actualizarEstado(ticket.getId(), estado);
            cargarTickets();
        });
    }

    private void actualizarTecnico(int ticketId, String tecnicoNombre) {
        String sql = """
        UPDATE ticket SET tecnico_id = (
            SELECT usuario_id FROM usuario WHERE nombre_completo = ?
        ) WHERE ticket_id = ?
    """;
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tecnicoNombre);
            stmt.setInt(2, ticketId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void asignarTecnico() {
        TicketTabla ticket = tablaTickets.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un ticket.", Alert.AlertType.WARNING);
            return;
        }

        ObservableList<String> tecnicos = FXCollections.observableArrayList();
        String sql = "SELECT nombre_completo FROM usuario WHERE rol_id = 2";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tecnicos.add(rs.getString("nombre_completo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, tecnicos);
        dialog.setTitle("Asignar Técnico");
        dialog.setHeaderText("Selecciona un técnico para el ticket");
        dialog.setContentText("Técnico:");

        dialog.showAndWait().ifPresent(tecnico -> {
            actualizarTecnico(ticket.getId(), tecnico);
            cargarTickets();
        });
    }

    private void eliminarTicket() {
        TicketTabla ticket = tablaTickets.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            try (Connection conn = ConexionDB.conectar()) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM ticket WHERE ticket_id = ?");
                stmt.setInt(1, ticket.getId());
                stmt.executeUpdate();
                listaTickets.remove(ticket);
                mostrarAlerta("Éxito", "Ticket eliminado correctamente.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo eliminar el ticket.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Selecciona un ticket para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void abrirVistaNuevoTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevoTicket.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) botonNuevoTicket.getScene().getWindow();
            stage.setScene(new Scene(root, 853, 483));
            stage.setTitle("Nuevo Ticket");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la vista de nuevo ticket.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
