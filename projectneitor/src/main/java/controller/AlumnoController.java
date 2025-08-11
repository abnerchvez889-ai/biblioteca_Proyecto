package controller;

import Model.Alumno;
import datastore.Datastore;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AlumnoController {

    @FXML private TableView<Alumno> tablaAlumnos;
    @FXML private TableColumn<Alumno, Integer> colId;
    @FXML private TableColumn<Alumno, String> colNombre;
    @FXML private TableColumn<Alumno, String> colMatricula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtMatricula;

    private final Datastore ds = Datastore.getInstance();
    private Alumno seleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        colMatricula.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("matricula"));
        tablaAlumnos.setItems(ds.obtenerAlumnos());

        tablaAlumnos.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            seleccionado = newV;
            if (newV != null) {
                txtNombre.setText(newV.getNombre());
                txtMatricula.setText(newV.getMatricula());
            } else limpiarFormulario();
        });
    }

    @FXML
    public void agregarAlumno() {
        try {
            String nombre = txtNombre.getText().trim();
            String matricula = txtMatricula.getText().trim();

            if (nombre.isEmpty() || matricula.isEmpty()) { mostrarAlerta("Rellena todos los campos"); return; }
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) { mostrarAlerta("El nombre solo puede contener letras y espacios"); return; }
            if (!matricula.matches("\\d+")) { mostrarAlerta("La matricula solo puede contener numeros"); return; }
            if (ds.existeAlumnoPorMatricula(matricula)) { mostrarAlerta("Ya existe una matricula igual"); return; }

            ds.agregarAlumno(nombre, matricula);
            tablaAlumnos.setItems(ds.obtenerAlumnos());
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta("Error al agregar alumno");
            e.printStackTrace();
        }
    }

    @FXML
    public void editarAlumno() {
        if (seleccionado == null) { mostrarAlerta("Selecciona un alumno para editar"); return; }
        String nombre = txtNombre.getText().trim();
        String matricula = txtMatricula.getText().trim();

        if (nombre.isEmpty() || matricula.isEmpty()) { mostrarAlerta("Rellena todos los campos"); return; }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) { mostrarAlerta("El nombre solo puede contener letras y espacios"); return; }
        if (!matricula.matches("\\d+")) { mostrarAlerta("La matricula solo puede contener numeros"); return; }
        if (!seleccionado.getMatricula().equalsIgnoreCase(matricula) && ds.existeAlumnoPorMatricula(matricula)) { mostrarAlerta("La matricula ya existe"); return; }

        ds.editarAlumno(seleccionado.getId(), nombre, matricula);
        tablaAlumnos.setItems(ds.obtenerAlumnos());
        limpiarFormulario();
    }

    @FXML
    public void eliminarAlumno() {
        Alumno a = tablaAlumnos.getSelectionModel().getSelectedItem();
        if (a == null) { mostrarAlerta("Selecciona un alumno para eliminar"); return; }
        ds.eliminarAlumno(a);
        tablaAlumnos.setItems(ds.obtenerAlumnos());
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtMatricula.clear();
        tablaAlumnos.getSelectionModel().clearSelection();
        seleccionado = null;
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Aviso");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
