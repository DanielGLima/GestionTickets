package org.example.gestiontickets;

import gestionTickets.CRUD.DepartamentoCRUD;
import gestionTickets.CRUD.TecnicoCRUD;
import gestionTickets.Departamento;
import gestionTickets.Tecnico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionDepartamentosController {

    @FXML private TableView<Departamento> tableDepartamentos;
    @FXML private TableColumn<Departamento, String> colNombre;
    @FXML private TableColumn<Departamento, String> colDescripcion;

    @FXML private TextField textDepartamento;
    @FXML private TextField textDescripcion;
    @FXML private ListView<Tecnico> listTecnicos;

    @FXML private Button botonCrear;
    @FXML private Button botonEditar;
    @FXML private Button botonEliminar;
    @FXML private Button botonAsignar;
    @FXML private Button botonRegresar;

    @FXML private ImageView Exit;
    @FXML private ImageView inicio;

    private final DepartamentoCRUD departamentoCRUD = new DepartamentoCRUD();
    private final TecnicoCRUD tecnicoCRUD = new TecnicoCRUD();

    private final ObservableList<Departamento> departamentos = FXCollections.observableArrayList();
    private final ObservableList<Tecnico> tecnicos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        botonRegresar.setOnAction(event -> volverAlInicio());
        colNombre.setCellValueFactory(d -> d.getValue().nombreProperty());
        colDescripcion.setCellValueFactory(d -> d.getValue().descripcionProperty());

        cargarDepartamentos();
        cargarTecnicos();

        tableDepartamentos.setItems(departamentos);
        listTecnicos.setItems(tecnicos);
        listTecnicos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableDepartamentos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                textDepartamento.setText(newSel.getNombre());
                textDescripcion.setText(newSel.getDescripcion());
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

    private void cargarDepartamentos() {
        departamentos.setAll(departamentoCRUD.obtenerDepartamento());
    }

    private void cargarTecnicos() {
        tecnicos.setAll(tecnicoCRUD.obtenerTecnicos());
    }

    @FXML
    private void crearDepartamento() {
        String nombre = textDepartamento.getText();
        String descripcion = textDescripcion.getText();

        if (!nombre.isBlank() && !descripcion.isBlank()) {
            departamentoCRUD.insertarDepartamento(nombre, descripcion);
            cargarDepartamentos();
            textDepartamento.clear();
            textDescripcion.clear();
        }
    }

    @FXML
    private void editarDepartamento() {
        Departamento seleccionado = tableDepartamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            departamentoCRUD.editarDepartamento(
                    seleccionado.getIdDepartamento(),
                    textDepartamento.getText(),
                    textDescripcion.getText()
            );
            cargarDepartamentos();
        }
    }

    @FXML
    private void eliminarDepartamento() {
        Departamento seleccionado = tableDepartamentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            departamentoCRUD.eliminarDepartamento(seleccionado.getIdDepartamento());
            cargarDepartamentos();
        }
    }

    @FXML
    private void asignarTecnicos() {
        Departamento seleccionado = tableDepartamentos.getSelectionModel().getSelectedItem();
        ObservableList<Tecnico> seleccionados = listTecnicos.getSelectionModel().getSelectedItems();

        if (seleccionado != null && !seleccionados.isEmpty()) {
            String idDepartamento = seleccionado.getIdDepartamento();
            departamentoCRUD.asignarTecnicosAlDepartamento(idDepartamento, seleccionados);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asignación exitosa");
            alert.setHeaderText(null);
            alert.setContentText("Técnicos asignados correctamente.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selección incompleta");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un departamento y al menos un técnico.");
            alert.showAndWait();
        }
    }

}
