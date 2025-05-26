package org.example.gestiontickets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HistorialTicketsController {
    @FXML
    private Label labelHistorial;

    @FXML
    public void initialize() {
        // Acción inicial al cargar la vista
        System.out.println("✅ Vista de Historial de Tickets cargada correctamente.");
        if (labelHistorial != null) {
            labelHistorial.setText("Aquí se mostrará el historial de tickets.");
        }
    }
}

