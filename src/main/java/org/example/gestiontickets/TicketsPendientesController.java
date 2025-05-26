package org.example.gestiontickets;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TicketsPendientesController {

        @FXML
        private Label labelInfo;

        @FXML
        public void initialize() {
            // Acción inicial al cargar la vista
            System.out.println("✅ Vista de Tickets Pendientes cargada correctamente.");
            if (labelInfo != null) {
                labelInfo.setText("Aquí se mostrarán los tickets pendientes.");
            }
        }
    }

