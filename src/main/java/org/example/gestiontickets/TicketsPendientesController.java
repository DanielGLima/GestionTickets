package org.example.gestiontickets;

import gestionTickets.ConexionDB;
import gestionTickets.SesionActual;
import gestionTickets.TicketPendienteTabla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketsPendientesController {

    @FXML private Label labelInfo;
    @FXML private TableView<TicketPendienteTabla> tableTicketsPendientes;
    @FXML private TableColumn<TicketPendienteTabla, Integer> colNo;
    @FXML private TableColumn<TicketPendienteTabla, String> colTitulo;
    @FXML private TableColumn<TicketPendienteTabla, String> colDepartamento;
    @FXML private TableColumn<TicketPendienteTabla, String> colPrioridad;
    @FXML private TableColumn<TicketPendienteTabla, String> colFechaDeCreacion;

    private final ObservableList<TicketPendienteTabla> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNo.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        colTitulo.setCellValueFactory(cell -> cell.getValue().tituloProperty());
        colDepartamento.setCellValueFactory(cell -> cell.getValue().departamentoProperty());
        colPrioridad.setCellValueFactory(cell -> cell.getValue().prioridadProperty());
        colFechaDeCreacion.setCellValueFactory(cell -> cell.getValue().fechaCreacionProperty());

        cargarTickets();
    }

    private void cargarTickets() {
        int tecnicoId = SesionActual.getUsuarioId();

        try (Connection conn = ConexionDB.conectar()) {

            // Obtener el departamento asignado
            String queryDepto = "SELECT departamento_id FROM tecnico_departamento WHERE tecnico_id = ?";
            PreparedStatement stmtDepto = conn.prepareStatement(queryDepto);
            stmtDepto.setInt(1, tecnicoId);
            ResultSet rsDepto = stmtDepto.executeQuery();

            if (rsDepto.next()) {
                int departamentoId = rsDepto.getInt("departamento_id");

                // Traer los tickets pendientes de ese departamento sin técnico asignado
                String sql = """
                    SELECT t.ticket_id, t.titulo, d.nombre AS departamento, t.prioridad, t.fecha_creacion
                    FROM Ticket t
                    JOIN Departamento d ON t.departamento_id = d.departamento_id
                    WHERE t.estado = 'Pendiente'
                      AND t.tecnico_asignado_id IS NULL
                      AND t.departamento_id = ?
                """;

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, departamentoId);
                ResultSet rs = stmt.executeQuery();

                lista.clear();

                while (rs.next()) {
                    TicketPendienteTabla ticket = new TicketPendienteTabla(
                            rs.getInt("ticket_id"),
                            rs.getString("titulo"),
                            rs.getString("departamento"),
                            rs.getString("prioridad"),
                            rs.getString("fecha_creacion")
                    );
                    lista.add(ticket);
                }

                tableTicketsPendientes.setItems(lista);

                labelInfo.setText(lista.isEmpty()
                        ? "No hay tickets pendientes para tu departamento."
                        : "Tickets cargados: " + lista.size());

            } else {
                labelInfo.setText("No estás asignado a ningún departamento.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            labelInfo.setText("Error al cargar tickets.");
        }
    }
}
