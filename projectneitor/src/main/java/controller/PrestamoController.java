package controller;

import Model.Alumno;
import Model.Libro;
import Model.Prestamo;
import datastore.Datastore;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class PrestamoController {

    @FXML private TableView<Prestamo> tablaPrestamos;
    @FXML private TableColumn<Prestamo, Integer> colId;
    @FXML private TableColumn<Prestamo, String> colAlumno;
    @FXML private TableColumn<Prestamo, String> colLibro;
    @FXML private TableColumn<Prestamo, String> colFechaPrestamo;

    @FXML private ComboBox<Alumno> cmbAlumno;
    @FXML private ComboBox<Libro> cmbLibro;

    private final Datastore ds = Datastore.getInstance();
    private Prestamo seleccionado = null;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colAlumno.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAlumno().getNombre()));
        colLibro.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLibro().getTitulo()));

        // Aqui se formatea la fecha para mostrar el dia/mes/año
        colFechaPrestamo.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getFechaPrestamo().format(formatter))
        );

        tablaPrestamos.setItems(ds.obtenerPrestamos());
        cmbAlumno.setItems(ds.obtenerAlumnos());
        cmbLibro.setItems(ds.obtenerLibrosDisponibles());

        tablaPrestamos.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            seleccionado = newV;
            if (newV != null) {
                cmbAlumno.setValue(newV.getAlumno());
                cmbLibro.setValue(newV.getLibro());
            } else {
                limpiarFormulario();
            }
        });
    }

    @FXML
    public void agregarPrestamo() {
        try {
            Alumno alumno = cmbAlumno.getValue();
            Libro libro = cmbLibro.getValue();

            if (alumno == null || libro == null) {
                mostrarAlerta("Selecciona alumno y libro.");
                return;
            }
            if (!libro.isDisponible()) {
                mostrarAlerta("El libro ya esta prestado.");
                return;
            }

            ds.agregarPrestamo(alumno, libro);
            cmbLibro.setItems(ds.obtenerLibrosDisponibles());
            tablaPrestamos.setItems(ds.obtenerPrestamos());
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta("Error al registrar prestamo.");
            e.printStackTrace();
        }
    }

    @FXML
    public void editarPrestamo() {
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un prestamo para editar.");
            return;
        }
        Alumno alumno = cmbAlumno.getValue();
        Libro libro = cmbLibro.getValue();

        if (alumno == null || libro == null) {
            mostrarAlerta("Selecciona alumno y libro.");
            return;
        }

        ds.actualizarPrestamo(seleccionado, alumno, libro);
        tablaPrestamos.setItems(ds.obtenerPrestamos());
        cmbLibro.setItems(ds.obtenerLibrosDisponibles());
        limpiarFormulario();
    }

    @FXML
    public void eliminarPrestamo() {
        Prestamo p = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (p == null) {
            mostrarAlerta("Selecciona un prestamo para eliminar.");
            return;
        }
        ds.eliminarPrestamo(p);
        tablaPrestamos.setItems(ds.obtenerPrestamos());
        cmbLibro.setItems(ds.obtenerLibrosDisponibles());
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        cmbAlumno.getSelectionModel().clearSelection();
        cmbLibro.getSelectionModel().clearSelection();
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
