package org.example.gestiontickets;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdministradorDashboardController {
    @FXML
    private VBox slider;

    @FXML
    private Label menuBack;

    @FXML
    private ImageView exit;

    private boolean isMenuVisible = true;

    @FXML
    public void initialize() {
        // Configurar acciones para los elementos del menú
        menuBack.setOnMouseClicked(event -> {
            toggleMenu();
        });

        exit.setOnMouseClicked(event -> {
            // Cerrar la aplicación
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });
    }

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        // Ocultar el menú después de seleccionar una opción
        if (isMenuVisible) {
            toggleMenu();
        }

        // Aquí puedes agregar la lógica para cambiar de vista según el botón presionado
        // Por ejemplo:
        // Button source = (Button) event.getSource();
        // String buttonText = source.getText();
        // switch(buttonText) { ... }
    }

    private void toggleMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.3));
        slide.setNode(slider);

        if (isMenuVisible) {
            slide.setToX(-slider.getWidth());
            isMenuVisible = false;
        } else {
            slide.setToX(0);
            isMenuVisible = true;
        }

        slide.play();
    }
}