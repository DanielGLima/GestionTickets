package org.example.gestiontickets;

import gestionTickets.ConexionDB;
import gestionTickets.TecnicoTabla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GestionTecnicosController {

    @FXML private TextField filtroNombre;
    @FXML private TextField filtroDepartamento;
    @FXML private TableView<TecnicoTabla> tablaTecnicos;
    @FXML private TableColumn<TecnicoTabla, Integer> colId;
    @FXML private TableColumn<TecnicoTabla, String> colNombre;
    @FXML private TableColumn<TecnicoTabla, String> colCorreo;
    @FXML private TableColumn<TecnicoTabla, String> colDepartamento;
    @FXML private Button botonAgregarTecnico;
    @FXML private Button botonEditarTecnico;
    @FXML private Button botonEliminarTecnico;
    @FXML private Button botonAsignarDepartamento;

    private ObservableList<TecnicoTabla> listaTecnicos = FXCollections.observableArrayList();
    private FilteredList<TecnicoTabla> filtro;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colCorreo.setCellValueFactory(data -> data.getValue().correoProperty());
        colDepartamento.setCellValueFactory(data -> data.getValue().departamentoProperty());

        cargarTecnicos();

        filtroNombre.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        filtroDepartamento.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        botonAgregarTecnico.setOnAction(e -> agregarTecnico());
        botonEditarTecnico.setOnAction(e -> editarTecnico());
        botonEliminarTecnico.setOnAction(e -> eliminarTecnico());
        botonAsignarDepartamento.setOnAction(e -> asignarDepartamento());
    }

    private void cargarTecnicos() {
        listaTecnicos.clear();
        String sql = """
            SELECT u.usuario_id, u.nombre_completo, u.correo_electronico, COALESCE(d.nombre, 'Sin departamento') AS departamento
            FROM usuario u
            LEFT JOIN departamento d ON u.departamento_id = d.departamento_id
            WHERE u.rol_id = 2
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaTecnicos.add(new TecnicoTabla(
                        rs.getInt("usuario_id"),
                        rs.getString("nombre_completo"),
                        rs.getString("correo_electronico"),
                        rs.getString("departamento")
                ));
            }

            filtro = new FilteredList<>(listaTecnicos, p -> true);
            tablaTecnicos.setItems(filtro);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los técnicos.", Alert.AlertType.ERROR);
        }
    }

    private void aplicarFiltros() {
        String nombre = filtroNombre.getText().toLowerCase();
        String departamento = filtroDepartamento.getText().toLowerCase();

        filtro.setPredicate(tecnico ->
                tecnico.getNombre().toLowerCase().contains(nombre) &&
                        tecnico.getDepartamento().toLowerCase().contains(departamento)
        );
    }

    private void agregarTecnico() {
        TextInputDialog dialogNombre = new TextInputDialog();
        dialogNombre.setTitle("Agregar Técnico");
        dialogNombre.setHeaderText("Ingrese el nombre completo del nuevo técnico:");
        dialogNombre.setContentText("Nombre:");

        dialogNombre.showAndWait().ifPresent(nombre -> {
            if (nombre.trim().isEmpty()) {
                mostrarAlerta("Advertencia", "El nombre no puede estar vacío.", Alert.AlertType.WARNING);
                return;
            }

            TextInputDialog dialogCorreo = new TextInputDialog();
            dialogCorreo.setTitle("Agregar Técnico");
            dialogCorreo.setHeaderText("Ingrese el correo electrónico del técnico:");
            dialogCorreo.setContentText("Correo:");

            dialogCorreo.showAndWait().ifPresent(correo -> {
                if (correo.trim().isEmpty()) {
                    mostrarAlerta("Advertencia", "El correo no puede estar vacío.", Alert.AlertType.WARNING);
                    return;
                }

                try (Connection conn = ConexionDB.conectar()) {
                    PreparedStatement stmt = conn.prepareStatement("""
                    INSERT INTO usuario (nombre_completo, correo_electronico, nombre_usuario, contrasena, rol_id)
                    VALUES (?, ?, ?, ?, 2)
                """);
                    stmt.setString(1, nombre);
                    stmt.setString(2, correo);
                    stmt.setString(3, correo.split("@")[0]); // nombre_usuario a partir del correo
                    stmt.setString(4, "1234"); // contraseña temporal
                    stmt.executeUpdate();
                    cargarTecnicos();
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo agregar el técnico. Verifique que el correo no esté duplicado.", Alert.AlertType.ERROR);
                }
            });
        });
    }

    private void editarTecnico() {
        TecnicoTabla tecnico = tablaTecnicos.getSelectionModel().getSelectedItem();
        if (tecnico == null) {
            mostrarAlerta("Advertencia", "Seleccione un técnico.", Alert.AlertType.WARNING);
            return;
        }

        TextInputDialog dialogNombre = new TextInputDialog(tecnico.getNombre());
        dialogNombre.setTitle("Editar Técnico");
        dialogNombre.setHeaderText("Editar nombre del técnico:");
        dialogNombre.setContentText("Nuevo nombre:");

        dialogNombre.showAndWait().ifPresent(nuevoNombre -> {
            if (nuevoNombre.trim().isEmpty()) {
                mostrarAlerta("Advertencia", "El nombre no puede estar vacío.", Alert.AlertType.WARNING);
                return;
            }

            TextInputDialog dialogCorreo = new TextInputDialog(tecnico.getCorreo());
            dialogCorreo.setTitle("Editar Técnico");
            dialogCorreo.setHeaderText("Editar correo del técnico:");
            dialogCorreo.setContentText("Nuevo correo:");

            dialogCorreo.showAndWait().ifPresent(nuevoCorreo -> {
                if (nuevoCorreo.trim().isEmpty()) {
                    mostrarAlerta("Advertencia", "El correo no puede estar vacío.", Alert.AlertType.WARNING);
                    return;
                }

                try (Connection conn = ConexionDB.conectar()) {
                    PreparedStatement stmt = conn.prepareStatement("""
                    UPDATE usuario
                    SET nombre_completo = ?, correo_electronico = ?
                    WHERE usuario_id = ?
                """);
                    stmt.setString(1, nuevoNombre);
                    stmt.setString(2, nuevoCorreo);
                    stmt.setInt(3, tecnico.getId());
                    stmt.executeUpdate();
                    cargarTecnicos();
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo editar el técnico.", Alert.AlertType.ERROR);
                }
            });
        });
    }

    private void eliminarTecnico() {
        TecnicoTabla tecnico = tablaTecnicos.getSelectionModel().getSelectedItem();
        if (tecnico == null) {
            mostrarAlerta("Advertencia", "Seleccione un técnico.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar Técnico");
        confirmacion.setHeaderText("¿Está seguro de eliminar al técnico?");
        confirmacion.setContentText(tecnico.getNombre());

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try (Connection conn = ConexionDB.conectar()) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuario WHERE usuario_id = ?");
                    stmt.setInt(1, tecnico.getId());
                    stmt.executeUpdate();
                    cargarTecnicos();
                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo eliminar el técnico.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void asignarDepartamento() {
        TecnicoTabla tecnico = tablaTecnicos.getSelectionModel().getSelectedItem();
        if (tecnico == null) {
            mostrarAlerta("Advertencia", "Seleccione un técnico.", Alert.AlertType.WARNING);
            return;
        }

        ObservableList<String> departamentos = FXCollections.observableArrayList();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM departamento");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                departamentos.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, departamentos);
        dialog.setTitle("Asignar Departamento");
        dialog.setHeaderText("Seleccione el nuevo departamento:");
        dialog.setContentText("Departamento:");

        dialog.showAndWait().ifPresent(dep -> {
            try (Connection conn = ConexionDB.conectar()) {
                PreparedStatement stmt = conn.prepareStatement("""
                    UPDATE usuario SET departamento_id = (
                        SELECT departamento_id FROM departamento WHERE nombre = ?
                    ) WHERE usuario_id = ?
                """);
                stmt.setString(1, dep);
                stmt.setInt(2, tecnico.getId());
                stmt.executeUpdate();
                cargarTecnicos();
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo asignar el departamento.", Alert.AlertType.ERROR);
            }
        });
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
